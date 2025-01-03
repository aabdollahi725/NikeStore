package com.example.nikestore.data.repo

import com.example.nikestore.data.source.ProductDataSource
import com.example.nikestore.data.source.ProductLocalDataSource
import com.example.nikestore.data.source.ProductRemoteDataSource
import com.sevenlearn.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(val localDataSource:ProductDataSource,val remoteDataSource: ProductDataSource):ProductRepository {
    override fun getProducts(): Single<List<Product>> {
        return remoteDataSource.getProducts()
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