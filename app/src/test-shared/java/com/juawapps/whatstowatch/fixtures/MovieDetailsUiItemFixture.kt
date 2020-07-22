package com.juawapps.whatstowatch.fixtures

import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.movies.ui.detail.MovieDetailsUiItem

class MovieDetailsUiItemFixture {
    operator fun invoke(
        update: MovieDetailsUiItem.() -> MovieDetailsUiItem = { this }
    ): MovieDetailsUiItem {
        return MovieDetailsUiItem(
            posterImage = ImageUrlFactory().createFromNull(),
            year = "2020",
            id = "1",
            language = "English",
            title = "Title",
            voteAverage = "10.0",
            voteColorAttr = R.attr.positiveColor,
            voteCount = "1",
            overview = "Overview"
        ).run(update)
    }
}