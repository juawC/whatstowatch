package com.juawapps.whatstowatch.movies.data.api

import com.juawapps.whatstowatch.movies.data.model.MovieDTO
import com.juawapps.whatstowatch.movies.data.model.ListResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/discover/movie")
    suspend fun discoverMovies(
        @Query("language") language: String = "en-UK"
    ): Response<ListResponseWrapper<MovieDTO>>


    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("language") language: String = "en-UK"
    ): Response<MovieDTO>
}
