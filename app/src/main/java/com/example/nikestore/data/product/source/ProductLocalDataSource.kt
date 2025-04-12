package com.example.nikestore.data.product.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nikestore.data.product.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource: ProductDataSource {
    override fun getAll(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM favorites")
    override fun getFavorites(): Single<List<Product>>

    @Delete
    override fun deleteFavoriteProduct(product: Product): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(product: Product): Completable
}