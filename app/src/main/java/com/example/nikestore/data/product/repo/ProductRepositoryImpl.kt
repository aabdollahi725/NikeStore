package com.example.nikestore.data.product.repo

import com.example.nikestore.data.product.source.ProductDataSource
import com.example.nikestore.data.product.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(val localDataSource: ProductDataSource, val remoteDataSource: ProductDataSource):
    ProductRepository {
    override fun getAll(sort:Int): Single<List<Product>> {
        return remoteDataSource.getAll(sort)
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