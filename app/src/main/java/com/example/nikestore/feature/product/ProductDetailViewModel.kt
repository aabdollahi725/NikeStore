package com.example.nikestore.feature.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.repo.CartRepo
import com.example.nikestore.data.comment.Comment
import com.example.nikestore.data.comment.repo.CommentRepository
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.repo.ProductRepository
import com.sevenlearn.nikestore.common.asyncRequest
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    commentRepository: CommentRepository,
    private val cartRepo: CartRepo,
    private val productRepository: ProductRepository
) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val addToCartProgressBarLiveData=MutableLiveData<Boolean>()

    init {
        progressBarLiveData.value = true
        productLiveData.value = savedStateHandle[EXTRA_KEY_DATA]
        commentRepository.getAll(productLiveData.value!!.id).asyncRequest()
            .doFinally {
                progressBarLiveData.value = false
            }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }

    fun addToCart(): Completable {
        addToCartProgressBarLiveData.postValue(true)
        return cartRepo.addToCart(productLiveData.value!!.id).doFinally {
            addToCartProgressBarLiveData.postValue(false)
        }.ignoreElement()
    }

    fun addToFavorites(product: Product){
        productRepository.addToFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("addToFavorites completed")
                }
            })
    }

    fun removeFromFavorites(product: Product){
        productRepository.deleteFavoriteProduct(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("removeFromFavorites completed")
                }
            })
    }

}