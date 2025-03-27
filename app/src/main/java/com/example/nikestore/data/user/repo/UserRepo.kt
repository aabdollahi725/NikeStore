package com.example.nikestore.data.user.repo

import io.reactivex.Completable

interface UserRepo {

    fun login(username:String, password:String):Completable
    fun signup(username:String, password:String):Completable
    fun loadToken()
    fun getUserName():String
    fun logout()
    fun saveThemeState(isDarkMode: Boolean)
    fun getThemeState(): Boolean
}