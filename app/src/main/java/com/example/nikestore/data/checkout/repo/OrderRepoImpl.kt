package com.example.nikestore.data.checkout.repo

import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.data.checkout.source.OrderDataSource
import io.reactivex.Single

class OrderRepoImpl(private val orderDataSource: OrderDataSource) : OrderRepo {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> =
        orderDataSource.submitOrder(firstName, lastName, postalCode, mobile, address, paymentMethod)

    override fun checkout(orderId: Int?): Single<Checkout> = orderDataSource.checkout(orderId!!)
}