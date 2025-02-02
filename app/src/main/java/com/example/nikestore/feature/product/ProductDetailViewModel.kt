package com.example.nikestore.feature.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.comment.repo.CommentRepository
import com.example.nikestore.data.product.Product
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import com.sevenlearn.nikestore.common.createComments
import timber.log.Timber

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    repository: CommentRepository
) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value=true
        productLiveData.value = savedStateHandle[EXTRA_KEY_DATA]
        repository.getAll(424).asyncNetWorkRequest()
            .doFinally{
                progressBarLiveData.value=false
            }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    Timber.i(t.toString())
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    commentsLiveData.value = createComments()
                }
            })
    }
}