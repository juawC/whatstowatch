package com.juawapps.whatstowatch.movies.data.mapper

import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetail
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val imageUrlFactory: ImageUrlFactory
) :
    Mapper<MovieListItemDTO, MovieDetail> {
    override fun map(input: MovieListItemDTO): MovieDetail {
        return with(input) {
            MovieDetail(
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