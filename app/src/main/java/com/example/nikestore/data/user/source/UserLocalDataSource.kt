package com.example.nikestore.data.user.source

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.nikestore.data.cart.MessageResponse
import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.TokenResponse
import io.reactivex.Single

const val ACCESS_TOKEN="access_token"
const val REFRESH_TOKEN="refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences) :UserDataSource{
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signup(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(sharedPreferences.getString(ACCESS_TOKEN,null),sharedPreferences.getString(
            REFRESH_TOKEN,null))
    }

    override fun saveToken(token: String, refreshToken: String?) {
       sharedPreferences.edit()
           .apply {
               putString("access_token",token)
               if(refreshToken!=null){
                   putString("refresh_token",refreshToken)
               }
           }
           .apply()
    }
}