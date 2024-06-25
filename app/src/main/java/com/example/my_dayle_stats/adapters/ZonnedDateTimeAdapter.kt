package com.example.my_dayle_stats.adapters

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonnedDateTimeAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(value: ZonedDateTime): String {
        return FORMATTER.format(value)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(value: String): ZonedDateTime {
        return LocalDateTime.parse(value, FORMATTER).atZone(ZONE_ID)
    }
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val ZONE_ID = ZoneId.systemDefault()
        @RequiresApi(Build.VERSION_CODES.O)
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }
}