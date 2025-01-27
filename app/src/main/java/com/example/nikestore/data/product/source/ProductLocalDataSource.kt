package com.example.nikestore.data.product.source

import com.example.nikestore.data.product.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource: ProductDataSource {
    override fun getAll(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteProduct(): Completable {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }
}