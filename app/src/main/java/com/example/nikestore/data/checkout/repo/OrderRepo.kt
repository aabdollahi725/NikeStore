package com.example.nikestore.data.checkout.repo

import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.SubmitOrderResult
import io.reactivex.Single

interface OrderRepo {
    fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        mobile: String,
        address: String,
        paymentMethod: String
    ):Single<SubmitOrderResult>

    fun checkout(orderId: Int?):Single<Checkout>
}