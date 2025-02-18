package com.example.nikestore.data.user

import timber.log.Timber

object TokenContainer {

    var token:String?=null
        private set
    var refresh_token:String?=null
        private set

    fun update(token:String?,refreshToken:String?){
        Timber.i("Access Token -> ${token?.substring(0,10)} , Refresh Token -> $refreshToken")
        this.token=token
        this.refresh_token=refreshToken
    }
}