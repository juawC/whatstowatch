package com.juawapps.whatstowatch.movies.domain.model

import com.juawapps.whatstowatch.common.domain.ImageUrl
import java.util.*

data class MovieListItem(
    val posterImage: ImageUrl,
    val releaseDate: Date,
    val genreIds: List<Int>,
    val id: Long,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val voteAverage: Float
)
