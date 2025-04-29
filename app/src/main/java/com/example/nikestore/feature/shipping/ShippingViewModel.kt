package com.example.nikestore.feature.shipping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.PurchaseDetail
import com.example.nikestore.data.checkout.SubmitOrderResult
import com.example.nikestore.data.order.repo.OrderRepo
import io.reactivex.Single

const val PAYMENT_METHOD_COD="cash_on_delivery"
const val PAYMENT_METHOD_ONLINE="online"

class ShippingViewModel(private val orderRepo: OrderRepo,savedStateHandle: SavedStateHandle): NikeViewModel() {
    val purchaseDetailLiveData= MutableLiveData<PurchaseDetail>()

    init {
        purchaseDetailLiveData.value=savedStateHandle[EXTRA_KEY_DATA]
    }

    fun submitOrder(firstName: String,
                    lastName: String,
                    postalCode: String,
                    mobile: String,
                    address: String,
                    paymentMethod: String):Single<SubmitOrderResult>{
       return orderRepo.submitOrder(firstName,lastName,postalCode,mobile,address,paymentMethod)
    }
}