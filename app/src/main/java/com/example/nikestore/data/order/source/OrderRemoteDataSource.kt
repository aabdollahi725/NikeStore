package com.example.nikestore.data.order.source

import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.data.order.OrderHistoryItem
import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(private val apiService: ApiService) : OrderDataSource {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> = apiService.submitOrder(JsonObject().apply {
        addProperty("first_name", firstName)
        addProperty("last_name", lastName)
        addProperty("postal_code", postalCode)
        addProperty("mobile", mobile)
        addProperty("address", address)
        addProperty("payment_method", paymentMethod)
    })

    override fun checkout(orderId: Int): Single<Checkout> = apiService.checkoutOrder(orderId)

    override fun getOrderHistory(): Single<List<OrderHistoryItem>> = apiService.getOrderHistory()
}