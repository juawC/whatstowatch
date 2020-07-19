package com.juawapps.whatstowatch.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juawapps.whatstowatch.movies.data.database.MovieListItemDao
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO

@Database(entities = [MovieListItemDTO::class], version = 2, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieListItemDao(): MovieListItemDao
}