package com.example.daon.data.community.token

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)