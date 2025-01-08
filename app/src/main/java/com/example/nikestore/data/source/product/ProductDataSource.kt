package com.example.nikestore.data.source.product

import com.sevenlearn.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun deleteFavoriteProduct(): Completable

    fun addFavoriteProduct(): Completable
}