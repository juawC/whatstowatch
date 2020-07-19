package com.juawapps.whatstowatch.movies.data.repository

import com.juawapps.whatstowatch.common.data.ListMapper
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.common.data.toResult
import com.juawapps.whatstowatch.movies.data.api.MoviesApi
import com.juawapps.whatstowatch.movies.data.database.MovieListItemDao
import com.juawapps.whatstowatch.movies.data.model.MovieDetailDTO
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesListItemDao: MovieListItemDao,
    private val moviesListMapper: ListMapper<MovieListItemDTO, MovieListItem>,
    private val moviesDetailsMapper: Mapper<MovieDetailDTO, MovieDetails>
) : MoviesRepository {
    override fun discoverMovies(): Flow<Result<List<MovieListItem>>> = flow {
        val result = try {
            moviesApi.discoverMovies().toResult { it.items }
        } catch (exception: Exception) {
            Result.Error(exception)
        }

        if (result is Result.Success) {
            moviesListItemDao.replaceAll(result.data)
            emit(Result.Success(moviesListMapper.map(moviesListItemDao.getAll())))
        } else {
            val getAll  = moviesListItemDao.getAll()
            if (getAll.isNotEmpty()) {
                emit(Result.Success(moviesListMapper.map(getAll)))
            } else {
                emit(result.map { emptyList<MovieListItem>() })
            }
        }
    }

    override fun getMovieDetails(id: Long): Flow<Result<MovieDetails>> = flow {
        val result = try {
            moviesApi.getMovieDetails(id).toResult(moviesDetailsMapper::map)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
        emit(result)
    }
}