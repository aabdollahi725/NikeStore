package com.example.nikestore.feature.auth

import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.user.repo.UserRepo
import io.reactivex.Completable

class AuthViewModel(private val userRepo: UserRepo):NikeViewModel() {

    fun login(username:String,password:String):Completable{
        progressBarLiveData.value=true
        return userRepo.login(username,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signup(username:String,password:String):Completable{
        progressBarLiveData.value=true
        return userRepo.signup(username,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }
}