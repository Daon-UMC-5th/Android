package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class FindPwRequestDto(
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String
)
