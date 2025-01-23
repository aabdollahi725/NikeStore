package com.example.nikestore.data.source.product

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource: ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteProduct(): Completable {
        TODO("Not yet implemented")
    }

    override fun addFavoriteProduct(): Completable {
        TODO("Not yet implemented")
    }
}