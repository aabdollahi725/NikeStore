package com.example.nikestore.data.user.source

import com.example.nikestore.data.user.SignupTokenResponse
import com.example.nikestore.data.user.LoginTokenResponse
import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

const val CLIENT_ID=2
const val CLIENT_SECRET="kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
const val PASSWORD="password"
class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun login(username: String, password: String): Single<LoginTokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("username", username)
            addProperty("grant_type", PASSWORD)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
            addProperty(PASSWORD, password)
        })
    }

    override fun signup(username: String, password: String): Single<SignupTokenResponse> {
        return apiService.signup(JsonObject().apply {
            addProperty("email", username)
            addProperty(PASSWORD, password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String?) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun saveThemeState(isDarkMode: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getThemeState(): Boolean {
        TODO("Not yet implemented")
    }
}