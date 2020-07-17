package com.juawapps.whatstowatch.movies.data.mapper

import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.common.domain.ImageUrl
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import javax.inject.Inject

class MovieListItemMapper @Inject constructor(
    private val imageUrlFactory: ImageUrlFactory
) :
    Mapper<MovieListItemDTO, MovieListItem> {
    override fun map(input: MovieListItemDTO): MovieListItem {
        return with(input) {
            MovieListItem(
                createPosterImage(posterPath),
                releaseDate,
                genreIds,
                id,
                originalTitle,
                originalLanguage,
                title,
                voteAverage
            )
        }
    }

    private fun createPosterImage(path: String?): ImageUrl {
        return if(path != null) {
            imageUrlFactory.createFromPoster(path)
        } else {
            imageUrlFactory.createFromNull()
        }
    }
}