package com.example.nikestore.feature.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.checkout.Checkout
import com.example.nikestore.data.checkout.repo.OrderRepo
import com.sevenlearn.nikestore.common.asyncRequest

class CheckoutViewModel(orderRepo: OrderRepo, savedStateHandle: SavedStateHandle) :
    NikeViewModel() {
    val orderId = MutableLiveData<Int>()
    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderId.value = savedStateHandle[EXTRA_KEY_ID]
        orderRepo.checkout(orderId.value)
            .asyncRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value=t
                }
            })
    }
}