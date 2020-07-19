package com.juawapps.whatstowatch.movies.data.mapper

import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.movies.data.model.MovieDetailDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val imageUrlFactory: ImageUrlFactory
) :
    Mapper<MovieDetailDTO, MovieDetails> {
    override fun map(input: MovieDetailDTO): MovieDetails {
        return with(input) {
            MovieDetails(
                imageUrlFactory.wrapNullToEmpty(posterPath, imageUrlFactory::createFromPoster),
                overview,
                releaseDate,
                id,
                originalTitle,
                originalLanguage,
                title,
                imageUrlFactory.wrapNullToEmpty(backdropPath, imageUrlFactory::createFromBackdrop),
                popularity,
                voteCount,
                voteAverage
            )
        }
    }
}