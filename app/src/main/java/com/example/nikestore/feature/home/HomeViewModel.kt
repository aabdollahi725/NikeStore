package com.example.nikestore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.banner.Banner
import com.example.nikestore.data.banner.repo.BannerRepository
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.SORT_NEWEST
import com.example.nikestore.data.product.SORT_POPULAR
import com.example.nikestore.data.product.repo.ProductRepository
import com.sevenlearn.nikestore.common.asyncNetWorkRequest

class HomeViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeViewModel() {

    private val _newestProducts = MutableLiveData<List<Product>>()
    val newestProducts: LiveData<List<Product>>
        get() = _newestProducts

    private val _popularProducts = MutableLiveData<List<Product>>()
    val popularProducts: LiveData<List<Product>>
        get() = _popularProducts

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>>
        get() = _banners

    init {
        progressBarLiveData.value = true
        productRepository.getAll(SORT_NEWEST)
            .asyncNetWorkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    _newestProducts.value=t
                }
            })

        productRepository.getAll(SORT_POPULAR)
            .asyncNetWorkRequest()
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    _popularProducts.value=t
                }
            })

        bannerRepository.getAll().asyncNetWorkRequest()
            .doFinally{progressBarLiveData.value=false}
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    _banners.value=t
                }
            })
    }
}