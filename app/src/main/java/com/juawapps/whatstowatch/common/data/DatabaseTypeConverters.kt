package com.juawapps.whatstowatch.common.data

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverters {
    private val apiDateFormatter = ApiDateFormatter()

    @TypeConverter
    fun fromString(value: String?): Date? {
        return value?.let { apiDateFormatter.parseDate(value) }
    }

    @TypeConverter
    fun dateString(date: Date?): String? {
        return date?.let(apiDateFormatter::format)
    }

    @TypeConverter
    fun fromIntList(list: List<Int>?): String? = list?.joinToString()

    @TypeConverter
    fun toIntList(list: String?): List<Int>? = list?.split(",")?.map { it.trim().toInt() }
}