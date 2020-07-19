package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.domain.ImageUrl
import com.juawapps.whatstowatch.common.ui.calendarYear
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem

data class MovieListUiItem(
    val posterImage: ImageUrl,
    val year: String,
    val id: Long,
    val language: String,
    val title: String,
    val voteAverage: String,
    val voteColorAttr: Int
) {

    companion object Factory {
        fun create(movieListItem: MovieListItem): MovieListUiItem {
            return with(movieListItem) {
                MovieListUiItem(
                    posterImage,
                    releaseDate.calendarYear.toString(),
                    id,
                    originalLanguage,
                    title,
                    String.format("%.1f", voteAverage),
                    if (voteAverage >= 7.0) R.attr.positiveColor else R.attr.mehColor
                )
            }
        }
    }
}
