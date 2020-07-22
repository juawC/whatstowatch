package com.juawapps.whatstowatch.fixtures

import com.juawapps.whatstowatch.common.data.ApiDateFormatter
import com.juawapps.whatstowatch.common.data.ImageUrlFactory
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails

class MovieDetailsFixture {
    operator fun invoke(update: MovieDetails.() -> MovieDetails = { this }): MovieDetails {
        return MovieDetails(
            posterPath = ImageUrlFactory().createFromNull(),
            overview = "Overview",
            releaseDate = ApiDateFormatter().parseDate("2020-01-10"),
            id = 1,
            originalTitle = "Title",
            originalLanguage = "English",
            title = "Title",
            backdropPath =  ImageUrlFactory().createFromNull(),
            popularity = 0.0f,
            voteAverage = 10.0f,
            voteCount = 1
        ).run(update)
    }
}