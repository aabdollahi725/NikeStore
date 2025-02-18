package com.example.nikestore.data.user.source

import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.user.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String, password:String): Single<TokenResponse>
    fun signup(username:String, password:String):Single<MessageResponse>
    fun loadToken()
    fun saveToken(token:String, refreshToken:String?)
}