package com.example.nikestore.data.source.product

import com.example.nikestore.services.http.ApiService
import com.sevenlearn.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getProducts(): Single<List<Product>> {
        return apiService.getProducts()
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