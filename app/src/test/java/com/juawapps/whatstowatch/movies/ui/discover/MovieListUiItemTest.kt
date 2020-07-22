package com.juawapps.whatstowatch.movies.ui.discover

import com.juawapps.whatstowatch.fixtures.MovieListItemFixture
import com.juawapps.whatstowatch.fixtures.MovieListUiItemFixture
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieListUiItemTest {

    val movieListUiItemFixture = MovieListUiItemFixture()
    val movieListItemFixture = MovieListItemFixture()

    @Test
    fun `create() when given default fixture it maps correctly`() {
        assertEquals(
            movieListUiItemFixture(),
            MovieListUiItem.create(movieListItemFixture())
        )
    }
}