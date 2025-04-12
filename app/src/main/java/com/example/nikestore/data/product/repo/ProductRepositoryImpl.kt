package com.example.nikestore.data.product.repo

import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val localDataSource: ProductDataSource,
    val remoteDataSource: ProductDataSource
) :
    ProductRepository {
    override fun getAll(sort: Int): Single<List<Product>> {
        return localDataSource.getFavorites()
            .flatMap { favoriteProducts ->
                remoteDataSource.getAll(sort).doOnSuccess {
                    val favoriteProductIds = favoriteProducts.map {
                        it.id
                    }
                    it.forEach {
                        if(favoriteProductIds.contains(it.id))
                            it.isFavorite=true
                    }
                }
            }
    }

    override fun getFavorites(): Single<List<Product>> = localDataSource.getFavorites()

    override fun deleteFavoriteProduct(product: Product): Completable =
        localDataSource.deleteFavoriteProduct(product)

    override fun addToFavorites(product: Product): Completable =
        localDataSource.addToFavorites(product)
}