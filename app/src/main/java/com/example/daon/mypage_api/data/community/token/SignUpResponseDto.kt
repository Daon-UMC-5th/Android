package com.example.daon.mypage_api.data.community.token

import com.google.gson.annotations.SerializedName

data class SignUpResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
)