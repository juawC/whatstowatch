package com.juawapps.whatstowatch.movies.domain.usecase

import com.juawapps.whatstowatch.common.data.Resource
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<MovieListItem>>> {
        return moviesRepository.discoverMovies()
    }
}