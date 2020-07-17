package com.juawapps.whatstowatch.common.data

import com.juawapps.whatstowatch.common.domain.ImageUrl

data class DefaultImageUrl(
    private val baseUrl: String,
    private val imageUrl: String,
    private val availableWidths: List<Int>
) : ImageUrl {
    override fun getUrl(targetWidth: Int): String {
        return formatUrlPath(
            availableWidths.firstOrNull { it > targetWidth } ?: availableWidths.last()
        )
    }

    private fun formatUrlPath(imageWidth: Int) = baseUrl + "w$imageWidth" + imageUrl
}