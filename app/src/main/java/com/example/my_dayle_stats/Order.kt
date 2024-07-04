package com.example.my_dayle_stats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverters
import com.example.my_dayle_stats.dao.OrderContracts
import com.example.my_dayle_stats.dao.ZdtConverter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
@Entity(tableName = OrderContracts.TABLE_NAME)
@TypeConverters(ZdtConverter::class)
data class Order(
    @ColumnInfo (name = OrderContracts.DATE)
    val date: ZonedDateTime,

    @ColumnInfo(name = OrderContracts.CHANGE_DATE)
    val lastChangeDate: ZonedDateTime,

    @ColumnInfo(name = OrderContracts.WH_NAME)
    val warehouseName: String,

    @ColumnInfo(name = OrderContracts.COUNTRY_NAME)
    val countryName: String,
    @ColumnInfo(name = OrderContracts.OBLAST_NAME)
    val oblastOkrugName: String,
    @ColumnInfo(name = OrderContracts.REGION_NAME)
    val regionName: String,
    @ColumnInfo(name = OrderContracts.SUPPLIER_ARTICLE)
    val supplierArticle: String,
    @ColumnInfo(name = OrderContracts.NM_ID)
    val nmId: Int,
    @ColumnInfo(name = OrderContracts.BARCODE)
    val barcode: String,
    @ColumnInfo(name = OrderContracts.CATEGORY)
    val category: String,
    @ColumnInfo(name = OrderContracts.SUBJECT)
    val subject: String,
    @ColumnInfo(name = OrderContracts.BRAND)
    val brand: String,
    @ColumnInfo(name = OrderContracts.TECHSIZE)
    val techSize: String,

    @ColumnInfo(name = OrderContracts.INCOME_ID)
    @Json(name="incomeID")
    val incomeId: Long,
    @ColumnInfo(name = OrderContracts.IS_SUPPLY)
    val isSupply: Boolean,
    @ColumnInfo(name = OrderContracts.IS_REALIZATION)
    val isRealization: Boolean,
    @ColumnInfo(name = OrderContracts.TOTAL_PRICE)
    val totalPrice: Long,
    @ColumnInfo(name = OrderContracts.DISCOUNT_PERCENT)
    val discountPercent: Long,
    @ColumnInfo(name = OrderContracts.SPP)
    val spp: Long,
    @ColumnInfo(name = OrderContracts.FINISHED_PRICE)
    val finishedPrice: Double,
    @ColumnInfo(name = OrderContracts.PRICE_WITH_DISCOUNT)
    val priceWithDisc: Double,
    @ColumnInfo(name = OrderContracts.IS_CANCEL)
    val isCancel: Boolean,
    @ColumnInfo(name = OrderContracts.CANCEL_DATE)
    val cancelDate: ZonedDateTime,
    @ColumnInfo(name = OrderContracts.ORDER_TYPE)
    val orderType: String,
    @ColumnInfo(name = OrderContracts.STICKER)
    val sticker: String,
    @ColumnInfo(name = OrderContracts.G_NUMBER)
    val gNumber: String,
    @PrimaryKey
    @ColumnInfo(name = OrderContracts.ID)
    val srid: String,

)
