package com.example.nikestore.data.user

data class LoginTokenResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)