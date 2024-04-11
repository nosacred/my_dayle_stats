package com.example.my_dayle_stats

import com.github.mikephil.charting.formatter.ValueFormatter

class ValueFormatterPieChart: ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "" + (value.toInt())
    }
}