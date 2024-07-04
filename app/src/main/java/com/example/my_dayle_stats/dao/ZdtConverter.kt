package com.example.my_dayle_stats.dao

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
@ProvidedTypeConverter
class ZdtConverter {
    @TypeConverter
    fun convertZdtToInstance(zdt : ZonedDateTime): Long = zdt.toEpochSecond()

    @TypeConverter
    fun convertInstanceToZdt(inst : Long): ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(inst), ZoneId.systemDefault())

}