package com.example.nikestore.data.user.source

import com.example.nikestore.data.user.SignupTokenResponse
import com.example.nikestore.data.user.LoginTokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String, password:String): Single<LoginTokenResponse>
    fun signup(username:String, password:String):Single<SignupTokenResponse>
    fun loadToken()
    fun saveToken(token:String, refreshToken:String?)
    fun saveUserName(username: String)
    fun getUserName():String
    fun logout()
}