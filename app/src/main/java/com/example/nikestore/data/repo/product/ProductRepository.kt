package com.example.nikestore.data.repo.product

import com.sevenlearn.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun deleteFavoriteProduct():Completable

    fun addFavoriteProduct():Completable
}