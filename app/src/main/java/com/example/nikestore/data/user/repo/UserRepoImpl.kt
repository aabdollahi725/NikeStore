package com.example.nikestore.data.user.repo

import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.LoginTokenResponse
import com.example.nikestore.data.user.source.UserLocalDataSource
import com.example.nikestore.data.user.source.UserRemoteDataSource
import io.reactivex.Completable

class UserRepoImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepo {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessLogin(it)
        }.ignoreElement()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccessLogin(it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    private fun onSuccessLogin(loginTokenResponse: LoginTokenResponse) {
        userLocalDataSource.saveToken(loginTokenResponse.access_token, loginTokenResponse.refresh_token)
        TokenContainer.update(loginTokenResponse.access_token, loginTokenResponse.refresh_token)
    }
}