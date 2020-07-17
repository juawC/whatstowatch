package com.juawapps.whatstowatch.common.utils

import java.text.SimpleDateFormat
import java.util.*

object ProjectDateUtils {
    private const val DATE_FORMAT_STRING = "dd-MM-yyyy"

    fun parseDate(dateString: String): Date {
        val sdf = SimpleDateFormat(DATE_FORMAT_STRING, Locale.UK)
        return sdf.parse(dateString) ?: throw Exception("Wrong date format!")
    }
}