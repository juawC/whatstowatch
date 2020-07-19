package com.juawapps.whatstowatch.movies.data.database

import androidx.room.*
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO

@Dao
abstract class  MovieListItemDao {
    @Query("DELETE FROM movielistitemdto")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM movielistitemdto ORDER BY indexInResponse ASC")
    abstract suspend fun getAll(): List<MovieListItemDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<MovieListItemDTO>)

    @Transaction
    open suspend fun replaceAll(list: List<MovieListItemDTO>) {
        deleteAll()
        val indexedList = list.mapIndexed { index, item ->
            item.apply { indexInResponse = index }
        }
        insertAll(indexedList)
    }
}