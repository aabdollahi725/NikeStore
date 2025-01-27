package com.example.nikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.banner.Banner
import com.example.nikestore.data.banner.repo.BannerRepository
import com.example.nikestore.data.product.repo.ProductRepository
import com.example.nikestore.data.product.Product
import com.example.nikestore.data.product.SORT_NEWEST
import com.example.nikestore.data.product.SORT_POPULAR
import com.sevenlearn.nikestore.common.createProducts
import com.sevenlearn.nikestore.common.createProducts2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
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

    val error = MutableLiveData<String>()

    init {
        progressBar.value = true
        productRepository.getAll(SORT_NEWEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBar.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    _newestProducts.value= createProducts()
                }
            })

        productRepository.getAll(SORT_POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    _popularProducts.value= createProducts2()
                }
            })

        bannerRepository.getAll().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressBar.value=false}
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    _banners.value=t
                }
            })
    }
}