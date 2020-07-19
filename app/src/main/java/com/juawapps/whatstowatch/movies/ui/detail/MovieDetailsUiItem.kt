package com.juawapps.whatstowatch.movies.ui.detail

import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.common.domain.ImageUrl
import com.juawapps.whatstowatch.common.ui.calendarYear
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.ui.shared.MovieUiItemFormatter

data class MovieDetailsUiItem(
    val posterImage: ImageUrl,
    val year: String,
    val id: String,
    val language: String,
    val title: String,
    val voteAverage: String,
    val voteColorAttr: Int,
    val voteCount: String,
    val overview: String
) {

    companion object Factory {
        fun create(movieListItem: MovieDetails): MovieDetailsUiItem {
            return with(movieListItem) {
                MovieDetailsUiItem(
                    posterPath,
                    releaseDate.calendarYear.toString(),
                    id.toString(),
                    originalLanguage,
                    title,
                    MovieUiItemFormatter.formatVoteAverage(voteAverage),
                    MovieUiItemFormatter.getVoteColorAttr(voteAverage),
                    voteCount.toString(),
                    overview
                )
            }
        }

        val EMPTY = MovieDetailsUiItem(
            ImageUrlFactory().createFromNull(),
            "",
            "",
            "",
            "",
            "",
            R.attr.colorOnBackground,
            "",
            ""
        )
    }
}
