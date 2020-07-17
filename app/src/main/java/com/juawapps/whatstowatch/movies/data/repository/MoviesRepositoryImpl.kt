package com.juawapps.whatstowatch.movies.data.repository

import com.juawapps.whatstowatch.common.data.ListMapper
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.common.data.toResult
import com.juawapps.whatstowatch.movies.data.api.MoviesApi
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetail
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesListMapper: ListMapper<MovieListItemDTO, MovieListItem>,
    private val moviesDetailMapper: Mapper<MovieListItemDTO, MovieDetail>
) : MoviesRepository {
    override fun discoverMovies(): Flow<Result<List<MovieListItem>>> = flow {
        val result = try {
            moviesApi.discoverMovies().toResult { moviesListMapper.map(it.items) }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
        emit(result)
    }

    override fun getMovieDetails(id: Long): Flow<Result<MovieDetail>> = flow {
        val result = try {
            moviesApi.getMovieDetails(id).toResult(moviesDetailMapper::map)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
        emit(result)
    }
}