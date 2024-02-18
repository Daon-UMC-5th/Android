package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class UserDeleteResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
