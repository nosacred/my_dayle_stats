package com.example.my_dayle_stats

data class AbClass(
    val nmId : String,
    val barcode: String,
    val supplierArticle: String,
    val category: String,
    val subject: String,
    val brand: String,
    val techSize: String,
    val cancelCountSale: Int,
    val countSale: Int,
    val totalSaleForPay:Int,
    val moneyBack: Int,
)