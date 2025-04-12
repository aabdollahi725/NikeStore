package com.example.nikestore.feature.main

import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.cart.CountResponse
import com.example.nikestore.data.cart.repo.CartRepo
import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.repo.UserRepo
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepo: CartRepo,private val userRepo: UserRepo):NikeViewModel() {

    fun getCartItemsCount(){
        if(!TokenContainer.token.isNullOrEmpty()){
            cartRepo.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeSingleObserver<CountResponse>(compositeDisposable) {
                    override fun onSuccess(t: CountResponse) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }


    val isDarkMode: Boolean
        get()=userRepo.getThemeState()
}