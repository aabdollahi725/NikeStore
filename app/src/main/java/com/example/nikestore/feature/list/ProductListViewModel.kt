package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeCompletableObserver
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.repo.ProductRepository
import com.sevenlearn.nikestore.common.asyncRequest
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductListViewModel(
    val repository: ProductRepository, savedStateHandle: SavedStateHandle,
) : NikeViewModel() {

    var productsLiveData = MutableLiveData<List<Product>>()
    var selectedSortLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.newestProducts,
        R.string.popularProducts,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )
    var sort: Int = savedStateHandle[EXTRA_KEY_DATA]!!

    init {
        getProducts(sort)
        selectedSortLiveData.value = sortTitles[sort]
    }

    fun getProducts(sort:Int) {
        progressBarLiveData.value=true
        repository.getAll(sort)
            .asyncRequest()
            .doFinally {
                progressBarLiveData.value=false
            }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value=t
                }
            })
    }

    fun addToFavorites(product: Product){
        if(product.isFavorite){
            repository.addToFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        Timber.i("addToFavorites completed")
                    }
                })
        }else{
            repository.deleteFavoriteProduct(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        Timber.i("removeFromFavorites completed")
                    }
                })
        }
    }

    fun onSelectedSortChangedByUser(sort: Int){
        this.sort=sort
        selectedSortLiveData.value=sortTitles[sort]
        getProducts(sort)
    }
}