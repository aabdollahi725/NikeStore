package com.example.nikestore.feature.shipping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.PurchaseDetail

class ShippingViewModel(savedStateHandle: SavedStateHandle): NikeViewModel() {
    val purchaseDetailLiveData= MutableLiveData<PurchaseDetail>()

    init {
        purchaseDetailLiveData.value=savedStateHandle[EXTRA_KEY_DATA]
    }
}