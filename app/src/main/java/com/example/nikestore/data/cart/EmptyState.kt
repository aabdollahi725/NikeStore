package com.example.nikestore.data.cart

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int=0,
    val ctaMustShow: Boolean=false,
)
