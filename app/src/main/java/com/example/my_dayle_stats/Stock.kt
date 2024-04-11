package com.example.my_dayle_stats
import android.os.Parcelable
import com.squareup.moshi.Json
import java.io.Serializable

data class Stock(val lastChangeDate: String,
                 val warehouseName: String,
                 val supplierArticle: String,
                 val nmId: Int,
                 val barcode: String,
                 val quantity: Int,
                 val inWayToClient: Int,
                 val inWayFromClient: Int,
                 val quantityFull: Int,
                 val category: String,
                 val subject: String,
                 val brand: String,
                 val techSize: String,
                 @Json(name = "Price")
                 val price: Double,
                 @Json(name="Discount")
                 val discount: Int,
                 val isSupply: Boolean,
                 val isRealization: Boolean,
                 @Json(name = "SCCode")
                 val sccode: String){
}