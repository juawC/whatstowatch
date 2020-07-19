package com.juawapps.whatstowatch.movies.data.api

import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.data.model.ListResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("language") language: String = "en-UK"
    ): Response<ListResponseWrapper<MovieListItemDTO>>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("language") language: String = "en-UK",
        @Query("page") page: Int = 1
    ): Response<MovieListItemDTO>
}
