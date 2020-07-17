package com.juawapps.whatstowatch.fixtures

import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.movies.ui.discover.MovieListUiItem

class MovieListUiItemFixture {
    operator fun invoke(update: MovieListUiItem.() -> Unit = {}): MovieListUiItem {
        return MovieListUiItem(
            posterImage = ImageUrlFactory().createFromNull(),
            year = "2020",
            id = 1,
            language = "English",
            title = "Title",
            voteAverage = "10.0",
            voteColorAttr = R.attr.positiveColor
        ).apply(update)
    }
}