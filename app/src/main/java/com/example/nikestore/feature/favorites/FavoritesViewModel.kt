package com.example.nikestore.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.repo.ProductRepository
import com.sevenlearn.nikestore.common.asyncRequest
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoritesViewModel(private val productRepository: ProductRepository): NikeViewModel() {
    val favoriteProducts= MutableLiveData<List<Product>>()
    init {
        productRepository.getFavorites()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    favoriteProducts.postValue(t)
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