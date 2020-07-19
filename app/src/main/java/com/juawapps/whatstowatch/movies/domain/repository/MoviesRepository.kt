package com.juawapps.whatstowatch.movies.domain.repository

import com.juawapps.whatstowatch.common.data.Resource
import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun discoverMovies(): Flow<Resource<List<MovieListItem>>>

    fun getMovieDetails(id: Long): Flow<Result<MovieDetails>>
}
