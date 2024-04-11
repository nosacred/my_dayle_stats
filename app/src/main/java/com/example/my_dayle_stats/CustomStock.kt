package com.example.my_dayle_stats

data class CustomStock(
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
    val price: Double,
    val discount: Int,
    val werehouseArray: Set<String>
)