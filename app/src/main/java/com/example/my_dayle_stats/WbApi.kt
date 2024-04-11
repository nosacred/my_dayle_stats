package com.example.my_dayle_stats

import com.example.my_dayle_stats.Sale
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WbApi {

    @GET("/api/v1/supplier/orders")
    fun searchOrders(
        @Query("dateFrom") dateFrom:String,
//        @Query("flag") flag: Int,
//        @Query("key") key: String,
        @Header("Authorization") auth :String

    ):Call<List<Order>>

    @GET("/api/v1/supplier/sales")
    fun searchSales(
        @Query("dateFrom") dateFrom:String,
//        @Query("flag") flag: Int,
//        @Query("key") key: String,
        @Header("Authorization") auth :String

    ):Call<List<Sale>>

    @GET("/api/v1/supplier/stocks")
    fun searchStocks(
        @Query("dateFrom") dateFrom:String,
//        @Query("flag") flag: Int,
//        @Query("key") key: String,
        @Header("Authorization") auth :String

    ):Call<List<Stock>>
}