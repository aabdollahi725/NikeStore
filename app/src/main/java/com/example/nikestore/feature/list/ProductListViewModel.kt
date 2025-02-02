package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.repo.ProductRepository
import com.sevenlearn.nikestore.common.asyncNetWorkRequest
import com.sevenlearn.nikestore.common.createProducts

class ProductListViewModel(
    val repository: ProductRepository, savedStateHandle: SavedStateHandle,
) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    var selectedSortLiveData=MutableLiveData<Int>()
    val sortTitles= arrayOf(R.string.newestProducts,R.string.popularProducts,R.string.sortPriceHighToLow,R.string.sortPriceLowToHigh)
    var sort:Int=savedStateHandle[EXTRA_KEY_DATA]!!
    init {
        getProducts(sort)
        selectedSortLiveData.value= sortTitles[sort]
    }

    fun getProducts(sort: Int) {
        progressBarLiveData.value = true
        repository.getAll(sort)
            .asyncNetWorkRequest()
            .doFinally {
                progressBarLiveData.value = false
            }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                }

                override fun onError(e: Throwable) {
                    productsLiveData.value = createProducts()
                }
            })
    }

    fun onSelectedSortChangedByUser(sort:Int){
        this.sort=sort
        this.selectedSortLiveData.value=sortTitles[sort]
        getProducts(sort)
    }
}