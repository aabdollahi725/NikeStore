package com.example.nikestore.data.user.source

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.nikestore.data.user.LoginTokenResponse
import com.example.nikestore.data.user.SignupTokenResponse
import com.example.nikestore.data.user.TokenContainer
import io.reactivex.Single
import androidx.core.content.edit

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(username: String, password: String): Single<LoginTokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signup(username: String, password: String): Single<SignupTokenResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN, null), sharedPreferences.getString(
                REFRESH_TOKEN, null
            )
        )
    }

    override fun saveToken(token: String, refreshToken: String?) {
        sharedPreferences.edit {
            putString("access_token", token)
            if (refreshToken != null) {
                putString("refresh_token", refreshToken)
            }
        }
    }

    override fun saveUserName(username: String) {
        sharedPreferences.edit {
            putString("username", username)
        }
    }

    override fun getUserName(): String {
        return sharedPreferences.getString("username","")?:""
    }

    override fun logout() {
        sharedPreferences.edit() {
            clear()
        }
    }

    override fun saveThemeState(isDarkMode: Boolean) {
        sharedPreferences.edit(){
            putBoolean("dark_mode_on",isDarkMode)
        }
    }

    override fun getThemeState(): Boolean {
        return sharedPreferences.getBoolean("dark_mode_on",false)
    }
}