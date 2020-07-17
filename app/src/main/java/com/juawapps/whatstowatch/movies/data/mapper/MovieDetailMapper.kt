package com.juawapps.whatstowatch.movies.data.mapper

import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val imageUrlFactory: ImageUrlFactory
) :
    Mapper<MovieListItemDTO, MovieDetails> {
    override fun map(input: MovieListItemDTO): MovieDetails {
        return with(input) {
            MovieDetails(
                imageUrlFactory.wrapNullToEmpty(posterPath, imageUrlFactory::createFromPoster),
                overview,
                releaseDate,
                genreIds,
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