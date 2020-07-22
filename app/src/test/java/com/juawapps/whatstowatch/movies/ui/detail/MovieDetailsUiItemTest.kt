package com.juawapps.whatstowatch.movies.ui.detail

import com.juawapps.whatstowatch.fixtures.MovieDetailsFixture
import com.juawapps.whatstowatch.fixtures.MovieDetailsUiItemFixture
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsUiItemTest {

    private val movieDetailFixture = MovieDetailsFixture()
    private val movieDetailUiItemFixture = MovieDetailsUiItemFixture()

    @Test
    fun `create() it returns a correctly parsed UiItem`() {
        // Arrange
        val aMovieDetail = movieDetailFixture()
        val aMovieDetailUiItem = movieDetailUiItemFixture()

        // Act
        val result = MovieDetailsUiItem.create(aMovieDetail)

        // Assert
        assertEquals(
            aMovieDetailUiItem,
            result
        )
    }
}