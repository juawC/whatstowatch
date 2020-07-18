package com.juawapps.whatstowatch.fixtures

import com.juawapps.whatstowatch.common.data.ApiDateFormatter
import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem

class MovieListItemFixture {
    operator fun invoke(update: MovieListItem.() -> Unit = {}): MovieListItem {
        return MovieListItem(
            posterImage = ImageUrlFactory().createFromNull(),
            releaseDate = ApiDateFormatter().parseDate("10-01-2020"),
            genreIds = emptyList(),
            id = 1,
            originalTitle = "Title",
            originalLanguage = "English",
            title = "Title",
            voteAverage = 10.0f
        ).apply(update)
    }
}