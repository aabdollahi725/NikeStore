package com.example.nikestore.data.user.repo

import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.TokenResponse
import com.example.nikestore.data.user.source.UserLocalDataSource
import com.example.nikestore.data.user.source.UserRemoteDataSource
import io.reactivex.Completable

class UserRepoImpl(
    val userRemoteDataSource: UserRemoteDataSource,
    val userLocalDataSource: UserLocalDataSource
) : UserRepo {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessLogin(it)
        }.ignoreElement()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username, password).doOnSuccess {
            onSuccessSignup(it)
        }.ignoreElement()    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    fun onSuccessLogin(tokenResponse: TokenResponse) {
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
    }

    fun onSuccessSignup(messageResponse: MessageResponse){
        userLocalDataSource.saveToken(messageResponse.message,null)
        TokenContainer.update(messageResponse.message, null)
    }
}