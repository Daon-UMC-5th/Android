package com.example.daon.mypage_api

import com.example.daon.data.PostBoard
import com.google.gson.annotations.SerializedName

data class PostBoardResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: com.example.daon.data.PostBoard
)