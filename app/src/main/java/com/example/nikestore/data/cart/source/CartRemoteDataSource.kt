package com.example.nikestore.data.cart.source

import com.example.nikestore.data.cart.AddToCarTResponse
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCarTResponse> = apiService.addToCart(JsonObject().apply {
            addProperty("product_id", productId)
    }
    )

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