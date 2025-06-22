package com.teebay.appname.framework.Util

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class TimeUtilTest {

    @Test
    fun `convert local datetime to UTC string correctly`() {
        // Given: A fixed local datetime (e.g. June 22, 2025 at 14:00)
        val input = "2025-06-22T14:00"

        // Override system time zone for consistent testing (e.g. Asia/Dhaka UTC+6)
        val zone = ZoneId.of("Asia/Dhaka")
        val localDateTime = LocalDateTime.parse(input)
        val zonedLocal = localDateTime.atZone(zone)
        val expectedUtc = zonedLocal.withZoneSameInstant(ZoneOffset.UTC).toString()

        // When: Calling the utility method
        val actualUtc = TimeUtil.convertLocalToUtcString(input)

        // Then: Result should match expected UTC time
        assertEquals(expectedUtc, actualUtc)
    }

    //TODO: Add invalid input test
}