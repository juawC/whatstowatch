package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.domain.ImageUrl
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem

data class MovieListUiItem(
    val posterImage: ImageUrl,
    val year: String,
    val id: Long,
    val language: String,
    val title: String,
    val voteAverage: String,
    val voteColor: Int
)

fun MovieListItem.mapToUi(): MovieListUiItem {
    return MovieListUiItem(
        posterImage,
        releaseDate.toString(),
        id,
        originalLanguage,
        title,
        voteAverage.toString(),
        R.color.green
    )
}
