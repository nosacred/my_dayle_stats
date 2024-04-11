package com.example.my_dayle_stats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class Sale(
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
           val totalPrice: Int,
           val discountPercent: Int,
           val spp: Int,
           val forPay: Double,
           val finishedPrice: Double,
           val priceWithDisc: Double,
           @Json(name="saleID")
           val saleId: String,
           val orderType: String,
           val sticker: String,
           val gNumber: String,
           val srid: String,
) {
}