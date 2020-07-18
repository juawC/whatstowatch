package com.juawapps.whatstowatch.common.data

import java.text.SimpleDateFormat
import java.util.*

class ApiDateFormatter {
    companion object {
        private const val DATE_FORMAT_STRING = "dd-MM-yyyy"
    }

    private val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING, Locale.UK)

    fun parseDate(dateString: String): Date {
        return dateFormat.parse(dateString) ?: throw Exception("Wrong date format!")
    }

    fun format(date: Date): String {
        return dateFormat.format(date)
    }
}