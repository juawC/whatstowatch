package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.common.domain.ImageUrl
import com.juawapps.whatstowatch.common.ui.calendarYear
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.ui.shared.MovieUiItemFormatter

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
                    MovieUiItemFormatter.formatVoteAverage(voteAverage),
                    MovieUiItemFormatter.getVoteColorAttr(voteAverage)
                )
            }
        }
    }
}
