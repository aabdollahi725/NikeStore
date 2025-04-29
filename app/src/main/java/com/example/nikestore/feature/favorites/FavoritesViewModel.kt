package com.example.nikestore.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.EmptyState
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.repo.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoritesViewModel(private val productRepository: ProductRepository) : NikeViewModel() {
    val favoriteProducts = MutableLiveData<List<Product>>()

    fun getFavoriteProducts() {
        productRepository.getFavorites()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                emptyStateLiveData.postValue(EmptyState(it.isEmpty()))
            }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    favoriteProducts.postValue(t)
                }
            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFavoriteProduct(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    favoriteProducts.value?.let {
                        if (it.isEmpty()) {
                            emptyStateLiveData.postValue(EmptyState(true))
                        }
                    }
                }
            })
    }

    fun addToFavorites(product: Product){
        productRepository.addToFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    if(emptyStateLiveData.value?.mustShow==true){
                        emptyStateLiveData.postValue(EmptyState(false))
                    }
                }
            })
    }
}