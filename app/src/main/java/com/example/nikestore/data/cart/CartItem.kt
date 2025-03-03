package com.example.nikestore.data.cart

import com.example.nikestore.data.product.Product

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var changeCountProgressBarIsVisible:Boolean=false
)