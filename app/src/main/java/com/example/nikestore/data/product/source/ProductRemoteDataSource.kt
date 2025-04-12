package com.example.nikestore.data.product.source

import com.example.nikestore.services.http.ApiService
import com.example.nikestore.data.product.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getAll(sort:Int): Single<List<Product>> {
        return apiService.getProducts(sort.toString())
    }

    override fun getFavorites(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteProduct(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }
}