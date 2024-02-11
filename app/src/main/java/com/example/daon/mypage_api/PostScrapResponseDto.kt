package com.example.daon.mypage_api

import com.example.daon.conect.calendar.data.PostScrap
import com.google.gson.annotations.SerializedName

data class PostScrapResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PostScrap
)
