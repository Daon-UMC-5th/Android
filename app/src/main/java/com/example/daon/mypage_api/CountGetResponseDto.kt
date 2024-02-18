package com.example.daon.mypage_api

import com.example.daon.data.ResultItem
import com.google.gson.annotations.SerializedName

data class CountGetResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code")val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<com.example.daon.data.ResultItem>
)


