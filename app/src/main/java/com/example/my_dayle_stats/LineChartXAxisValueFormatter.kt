package com.example.my_dayle_stats

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis(value.toLong())

        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat = SimpleDateFormat("dd MMM")
        return dateTimeFormat.format(timeMilliseconds)
    }
}