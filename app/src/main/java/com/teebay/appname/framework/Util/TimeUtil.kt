package com.teebay.appname.framework.Util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object TimeUtil {

    //TODO: Handle invalid datetime
    fun convertLocalToUtcString(startDateTime: String): String {
        // Parse the local date-time string
        val localDateTime = LocalDateTime.parse(startDateTime)

        // Convert to ZonedDateTime in the system's local time zone
        val zonedLocal = localDateTime.atZone(ZoneId.systemDefault())

        // Convert to UTC ZonedDateTime
        val utcZoned = zonedLocal.withZoneSameInstant(ZoneOffset.UTC)

        // Return the UTC time as an ISO 8601 string
        return utcZoned.toString() // e.g., 2025-06-22T08:00Z
    }
}