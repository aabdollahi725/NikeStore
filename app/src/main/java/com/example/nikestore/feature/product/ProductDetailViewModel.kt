package com.example.nikestore.feature.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.repo.CartRepo
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.comment.repo.CommentRepository
import com.example.nikestore.data.product.Product
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import io.reactivex.Completable

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    commentRepository: CommentRepository,
    val cartRepo: CartRepo
) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        productLiveData.value = savedStateHandle[EXTRA_KEY_DATA]
        commentRepository.getAll(productLiveData.value!!.id).asyncNetWorkRequest()
            .doFinally {
                progressBarLiveData.value = false
            }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }

    fun addToCart(): Completable = cartRepo.addToCart(productLiveData.value!!.id).ignoreElement()

}