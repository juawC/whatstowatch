package com.juawapps.whatstowatch.movies.domain.usecase

import com.juawapps.whatstowatch.common.data.Result
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Long): Flow<Result<MovieDetails>> {
        return moviesRepository.getMovieDetails(id)
    }
}