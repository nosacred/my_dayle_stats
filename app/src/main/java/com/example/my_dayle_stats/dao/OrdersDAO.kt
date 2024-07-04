package com.example.my_dayle_stats.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.my_dayle_stats.Order
@Dao
interface OrdersDAO  {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertOrders(orders : List<Order>)

    suspend fun getAllOrders():List<Order>


}