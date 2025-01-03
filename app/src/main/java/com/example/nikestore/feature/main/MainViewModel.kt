package com.example.nikestore.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.repo.ProductRepository
import com.sevenlearn.nikestore.data.Product
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository) : NikeViewModel() {

    val products=MutableLiveData<List<Product>>()
    val error= MutableLiveData<String>()

    init {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    error.value=e.toString()
                }

                override fun onSuccess(t: List<Product>) {
                    products.value=t
                }

            })
    }
}