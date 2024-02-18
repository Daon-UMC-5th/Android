package com.example.daon.conect.authorization

import com.google.gson.annotations.SerializedName

data class EmailCodeCheckResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
