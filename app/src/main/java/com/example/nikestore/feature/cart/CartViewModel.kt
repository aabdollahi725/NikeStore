package com.example.nikestore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.CartItem
import com.example.nikestore.data.cart.CartResponse
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.EmptyState
import com.example.nikestore.data.cart.PurchaseDetail
import com.example.nikestore.data.cart.repo.CartRepo
import com.example.nikestore.data.user.TokenContainer
import com.sevenlearn.nikestore.common.asyncRequest
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class CartViewModel(private val repo: CartRepo) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun get() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            repo.get()
                .asyncRequest()
                .doFinally {
                    progressBarLiveData.value = false
                }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.payable_price, t.shipping_cost, t.total_price)
                            emptyStateLiveData.value = EmptyState(false)
                        } else {
                            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                        }
                    }
                })
        } else {
            emptyStateLiveData.value = EmptyState(true, R.string.cartLoginEmptyState, true)
        }
    }

    fun removeCartItem(cartItem: CartItem): Completable {
        return repo.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                Timber.i(cartItemsLiveData.value?.size.toString())
                updatePurchaseDetail()

                val cartItemsCount=EventBus.getDefault().getStickyEvent(CountResponse::class.java)
                cartItemsCount?.let {
                    it.count--
                    EventBus.getDefault().postSticky(it)
                }

                if (cartItemsLiveData.value!!.isEmpty())
                    emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState))
            }.ignoreElement()
    }

    fun increaseItemCount(cartItem: CartItem): Completable {
        var cartItemCount = cartItem.count
        return repo.changeCount(cartItem.cart_item_id, ++cartItemCount)
            .doAfterSuccess {
                cartItem.count++
                updatePurchaseDetail()
            }.ignoreElement()
    }

    fun decreaseItemCount(cartItem: CartItem): Completable {
        var cartItemCount = cartItem.count
        return repo.changeCount(
            cartItem.cart_item_id, --cartItemCount
        )
            .doAfterSuccess {
                cartItem.count--
                updatePurchaseDetail()
            }.ignoreElement()
    }

    fun refresh() {
        get()
    }

    private fun updatePurchaseDetail() {
        var totalPrice = 0
        var payablePrice = 0
        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }

    }
}