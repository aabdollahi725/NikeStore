package com.example.nikestore.data.cart

import com.example.nikestore.data.product.Product

data class CartItem(
    val cart_item_id: Int,
    val count: Int,
    val product: Product
)