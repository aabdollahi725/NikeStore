package com.example.nikestore.data.product.repo

import com.example.nikestore.data.product.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getAll(sort:Int): Single<List<Product>>

    fun getFavorites(): Single<List<Product>>

    fun deleteFavoriteProduct(product: Product):Completable

    fun addToFavorites(product: Product):Completable
}