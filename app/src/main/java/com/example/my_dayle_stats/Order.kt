package com.example.my_dayle_stats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class Order(
    val date: ZonedDateTime,
    val lastChangeDate: ZonedDateTime,
    val warehouseName: String,
    val countryName: String,
    val oblastOkrugName: String,
    val regionName: String,
    val supplierArticle: String,
    val nmId: Int,
    val barcode: String,
    val category: String,
    val subject: String,
    val brand: String,
    val techSize: String,
    @Json(name="incomeID")
    val incomeId: Long,
    val isSupply: Boolean,
    val isRealization: Boolean,
    val totalPrice: Long,
    val discountPercent: Long,
    val spp: Long,
    val finishedPrice: Double,
    val priceWithDisc: Double,
    val isCancel: Boolean,
    val cancelDate: ZonedDateTime,
    val orderType: String,
    val sticker: String,
    val gNumber: String,
    val srid: String,

)
