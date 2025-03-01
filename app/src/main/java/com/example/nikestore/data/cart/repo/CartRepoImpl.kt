package com.example.nikestore.data.cart.repo

import com.example.nikestore.data.cart.AddToCarTResponse
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.cart.source.CartDataSource
import io.reactivex.Single

class CartRepoImpl(private val remoteDataSource: CartDataSource) : CartRepo {
    override fun addToCart(productId: Int): Single<AddToCarTResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = remoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> =
        remoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCarTResponse> =
        remoteDataSource.changeCount(cartItemId, count)

    override fun getCartItemsCount(): Single<CountResponse> = remoteDataSource.getCartItemsCount()
}