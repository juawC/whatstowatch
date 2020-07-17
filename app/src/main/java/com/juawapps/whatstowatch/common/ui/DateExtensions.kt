package com.juawapps.whatstowatch.common.ui

import java.util.*

val Date.calendarYear: Int
    get() {
        val calendar = Calendar.getInstance().apply { time = this@calendarYear }

        return calendar.get(Calendar.YEAR)
    }