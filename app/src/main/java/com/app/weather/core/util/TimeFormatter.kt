package com.app.weather.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatUnixTime(
        unixSeconds: Double,
        timeZoneId: String = TimeZone.getDefault().id
    ): String {
        val date = Date(unixSeconds.toLong() * 1000) // seconds → milliseconds
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(date)
    }
