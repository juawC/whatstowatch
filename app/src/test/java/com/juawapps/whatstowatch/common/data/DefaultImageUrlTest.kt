package com.juawapps.whatstowatch.common.data

import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultImageUrlTest {

    private val baseUrl: String = "baseUrl/"
    private val imageUrl: String = "/imageUrl"
    private val widths = arrayListOf(92, 154, 185, 342, 500, 780)

    @Test
    fun `getUrl() when targetWidth is within the available ranges it gets the immediate value above`() {
        val imageUrl = DefaultImageUrl(baseUrl, imageUrl, widths)

        assertEquals("baseUrl/w342/imageUrl", imageUrl.getUrl(190))
    }

    @Test
    fun `getUrl() when targetWidth is bellow the available ranges it gets the first value`() {
        val imageUrl = DefaultImageUrl(baseUrl, imageUrl, widths)

        assertEquals("baseUrl/w92/imageUrl", imageUrl.getUrl(90))
    }

    @Test
    fun `getUrl() when targetWidth is above the available ranges it gets the last value`() {
        val imageUrl = DefaultImageUrl(baseUrl, imageUrl, widths)

        assertEquals("baseUrl/w780/imageUrl", imageUrl.getUrl(900))
    }
}