package com.example.nikestore.data.checkout

data class SubmitOrderResult(
    val bank_gateway_url: String,
    val order_id: Int
)