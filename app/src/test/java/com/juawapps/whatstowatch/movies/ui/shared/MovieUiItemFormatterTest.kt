package com.juawapps.whatstowatch.movies.ui.shared

import com.juawapps.whatstowatch.R
import org.junit.Assert.*
import org.junit.Test

class MovieUiItemFormatterTest {

    @Test
    fun `getVoteColorAttr() when voteAverage is less than 7 it gets meh voteColorAttr`() {

        assertEquals(
            R.attr.mehColor,
            MovieUiItemFormatter.getVoteColorAttr(5.0f)
        )
    }

    @Test
    fun `getVoteColorAttr() when voteAverage is greater than 07 it gets positive voteColorAttr`() {

        assertEquals(
            R.attr.positiveColor,
            MovieUiItemFormatter.getVoteColorAttr(8.0f)
        )
    }

    @Test
    fun `formatVoteAverage() it formats with one decimal place`() {

        assertEquals(
            "8.0",
            MovieUiItemFormatter.formatVoteAverage(8.0f)
        )
    }
}