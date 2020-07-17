package com.juawapps.whatstowatch.movies.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieDTO(
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "overview") val overview: String,
    @Json(name = "release_date") val releaseDate: Date,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "id") val id: Int,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "title") val title: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "popularity") val popularity: Float,
    @Json(name = "vote_count") val voteCount: Long,
    @Json(name = "vote_average") val video: Float
)
