package com.juawapps.whatstowatch.common.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.util.*

class MoshiCustomDateAdapter : JsonAdapter<Date>() {
    private val apiDateFormatter = ApiDateFormatter()

    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            apiDateFormatter.parseDate(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.let(apiDateFormatter::format))
    }
}