package com.example.nikestore.data.product.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun productDao(): ProductLocalDataSource
}