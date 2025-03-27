package com.example.nikestore.feature.settings

import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.data.user.repo.UserRepo

class SettingsViewModel(private val userRepo: UserRepo): NikeViewModel() {
    val isDarkMode: Boolean
    get() = userRepo.getThemeState()

    fun saveThemeState(isDarkMode: Boolean){
        userRepo.saveThemeState(isDarkMode)
    }
}