package com.example.my_dayle_stats.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.my_dayle_stats.Order

@Database(entities = [Order::class], version = MyDatabase.DB_VERSION)
abstract class MyDatabase: RoomDatabase() {

    abstract fun ordersDao(): OrdersDAO

    companion object{
        const val DB_VERSION = 1
        const val DB_NAME = "my_database"
    }
}

