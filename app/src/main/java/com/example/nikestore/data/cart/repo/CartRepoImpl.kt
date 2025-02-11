package com.example.nikestore.data.cart.repo

import com.example.nikestore.data.cart.AddToCarTResponse
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.cart.source.CartDataSource
import io.reactivex.Single

class CartRepoImpl(val remoteDataSource: CartDataSource) : CartRepo {
    override fun addToCart(productId: Int): Single<AddToCarTResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCarTResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CountResponse> {
        TODO("Not yet implemented")
    }
}