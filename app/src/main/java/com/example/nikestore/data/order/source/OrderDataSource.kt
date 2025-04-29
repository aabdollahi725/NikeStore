package com.example.nikestore.data.order.source

import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.data.order.OrderHistoryItem
import io.reactivex.Single

interface OrderDataSource {
    fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ):Single<SubmitOrderResult>

    fun checkout(orderId:Int):Single<Checkout>

    fun getOrderHistory():Single<List<OrderHistoryItem>>
}