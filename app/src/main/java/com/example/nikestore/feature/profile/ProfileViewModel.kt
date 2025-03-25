package com.example.nikestore.feature.profile

import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.user.TokenContainer
import com.example.nikestore.data.user.repo.UserRepo

class ProfileViewModel(private val userRepo: UserRepo):NikeViewModel() {
    val username:String
        get() = userRepo.getUserName()

    val isLogin:Boolean
        get() = TokenContainer.token!=null

    fun logout(){
        userRepo.logout()
    }
}