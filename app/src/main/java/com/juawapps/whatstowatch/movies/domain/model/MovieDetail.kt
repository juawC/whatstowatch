package com.juawapps.whatstowatch.movies.domain.model

import com.juawapps.whatstowatch.common.domain.ImageUrl
import java.util.*

data class MovieDetail(
    val posterPath: ImageUrl,
    val overview: String,
    val releaseDate: Date,
    val genreIds: List<Int>,
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: ImageUrl,
    val popularity: Float,
    val voteCount: Long,
    val voteAverage: Float
)
