package com.juawapps.whatstowatch.common.data

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ApiDateFormatterTest {

    private val dateFormatter = ApiDateFormatter()
    private val aDate = createDate(
        day = 10,
        month = 1,
        year = 2020
    )
    private val aFormattedDate = "2020-01-10"

    @Test
    fun `parseDate() it returns the expected date`() {
        // Act
        val result = dateFormatter.parseDate(aFormattedDate)

        // Result
        assertEquals(
            aDate,
            result
        )
    }

    @Test
    fun `format() it returns the formatted date`() {
        // Act
        val result = dateFormatter.format(aDate)

        // Assert
        assertEquals(
            aFormattedDate,
            result
        )
    }

    private fun createDate(day: Int, month: Int, year: Int) : Date {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month - 1)
            set(Calendar.YEAR, year)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
        }
        return calendar.time
    }
}