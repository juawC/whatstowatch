package com.juawapps.whatstowatch.common.data

import com.squareup.moshi.*
import java.util.*

class MoshiCustomDateAdapter : JsonAdapter<Date>() {
    private val apiDateFormatter = ApiDateFormatter()

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            apiDateFormatter.parseDate(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.let(apiDateFormatter::format))
    }
}