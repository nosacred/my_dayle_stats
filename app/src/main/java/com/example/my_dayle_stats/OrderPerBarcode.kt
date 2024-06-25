package com.example.my_dayle_stats

import com.squareup.moshi.Json
import java.time.ZonedDateTime

data class OrderPerBarcode (

    val warehouseName: String,
    val supplierArticle: String,
    val nmId: Int,
    val barcode: String,
    val category: String,
    val subject: String,
    val brand: String,
    val techSize: String,
    val count : Int

    )
