package com.juawapps.whatstowatch.common.domain

interface ImageUrl {
    fun getUrl(targetWidth: Int = Int.MAX_VALUE): String
}