package com.juawapps.whatstowatch.movies.data.repository

import com.juawapps.whatstowatch.common.data.*
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
    override fun discoverMovies(): Flow<Resource<List<MovieListItem>>> = flow {
        val cachedMovies  = moviesListMapper.map(moviesListItemDao.getAll())
        emit(Resource.Loading(cachedMovies))

        val apiResult = try {
            moviesApi.discoverMovies().toResult { it.items }
        } catch (exception: Exception) {
            Result.Error(exception)
        }

        if (apiResult is Result.Success) {
            moviesListItemDao.replaceAll(apiResult.data)
            val updatedMovies  = moviesListItemDao.getAll()
            emit(Resource.Success(moviesListMapper.map(updatedMovies)))
        } else if (apiResult is Result.Error) {
            emit(Resource.Error(apiResult.exception, cachedMovies))
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