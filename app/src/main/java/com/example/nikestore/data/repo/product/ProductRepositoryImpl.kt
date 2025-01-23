package com.example.nikestore.data.repo.product

import com.example.nikestore.data.source.product.ProductDataSource
import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(val localDataSource: ProductDataSource, val remoteDataSource: ProductDataSource):
    ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> {
        return remoteDataSource.getProducts(sort)
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