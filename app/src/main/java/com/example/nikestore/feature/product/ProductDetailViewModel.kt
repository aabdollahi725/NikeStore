package com.example.nikestore.feature.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.Product

class ProductDetailViewModel(savedStateHandle: SavedStateHandle) : NikeViewModel() {

    val productLiveData=MutableLiveData<Product>()

    init {
        productLiveData.value= savedStateHandle[EXTRA_KEY_DATA]
    }
}