package com.example.nikestore.data.cart.source

import com.example.nikestore.data.cart.AddToCarTResponse
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId:Int): Single<AddToCarTResponse>

    fun get():Single<CartResponse>

    fun remove(cartItemId:Int):Single<MessageResponse>

    fun changeCount(cartItemId:Int,count:Int):Single<AddToCarTResponse>

    fun getCartItemsCount():Single<CountResponse>
}