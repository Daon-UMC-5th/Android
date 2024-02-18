package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email") private val email: String,
    @SerializedName("password") private val password: String
)
