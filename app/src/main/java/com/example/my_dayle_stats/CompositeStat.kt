package com.example.my_dayle_stats

data class CompositeStat(
    val warehouseNameList: List<String>,
    val supplierArticle: String?,
    val nmId: Int,
    val barcode: String,
    val quantity: Int,
    val inWayToClient: Int,
    val inWayFromClient: Int,
    val category: String,
    val subject: String,
    val brand: String,
    val techSize: String,
    val regionNameList: List<String>,
    val cancelCountOrder: Int,
    val cancelCountSale: Int,
    val countSale: Int,
    val countOrder: Int,
    val totalOrdersPrice:Int,
    val totalSaleForPay:Int,
    val moneyBack: Int

) {
}