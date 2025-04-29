package com.example.nikestore.feature.order

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.EmptyState
import com.example.nikestore.data.order.OrderHistoryItem
import com.example.nikestore.data.order.repo.OrderRepo
import com.example.nikestore.data.user.TokenContainer
import io.reactivex.schedulers.Schedulers

class OrderHistoryViewModel(private val orderRepo: OrderRepo) : NikeViewModel() {
    val orderHistoryItems = MutableLiveData<List<OrderHistoryItem>>()

    private fun get(){
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            orderRepo.getOrderHistory()
                .subscribeOn(Schedulers.io())
                .doFinally {
                    progressBarLiveData.postValue(false)
                }
                .subscribe(object :
                    NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                    override fun onSuccess(t: List<OrderHistoryItem>) {
                        if (t.isEmpty()) {
                            emptyStateLiveData.postValue(
                                EmptyState(
                                    true,
                                    R.string.orderHistoryEmptyState,
                                    false
                                )
                            )
                        } else {
                            emptyStateLiveData.postValue(
                                EmptyState(
                                    false
                                )
                            )
                            orderHistoryItems.postValue(t)
                        }
                    }
                })
        } else {
            emptyStateLiveData.value = EmptyState(true, R.string.loginEmptyState, true)
        }
    }

    fun refresh(){
        get()
    }
}