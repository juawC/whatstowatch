package com.juawapps.whatstowatch.common.data

import com.juawapps.whatstowatch.common.domain.ImageUrl
import javax.inject.Inject

class ImageUrlFactory @Inject constructor(){

    companion object {
        private const val DEFAULT_URL = "https://image.tmdb.org/t/p/"
    }

    fun createFromPoster(
        imageUrl: String
    ) = DefaultImageUrl(
        DEFAULT_URL,
        imageUrl,
        arrayListOf(92, 154, 185, 342, 500, 780)
    )

    fun createFromBackdrop(
        imageUrl: String
    ) = DefaultImageUrl(
        DEFAULT_URL,
        imageUrl,
        arrayListOf(300, 780, 1280)
    )

    fun createFromNull() = DefaultImageUrl("", "", emptyList())

    fun wrapNullToEmpty(imageUrl: String?, creator: (String) -> ImageUrl): ImageUrl {
        return if (imageUrl != null) creator(imageUrl) else createFromNull()
    }
}