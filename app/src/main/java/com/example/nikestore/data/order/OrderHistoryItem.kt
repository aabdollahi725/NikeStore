package com.example.nikestore.data.order

data class OrderHistoryItem(
    val id: Int,
    val order_items: List<OrderItem>,
    val payable: Int
)