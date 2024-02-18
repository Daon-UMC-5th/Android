package com.example.daon.mypage_api.data.community

import com.example.daon.mypage_api.data.community.data.CommentCall
import com.google.gson.annotations.SerializedName

data class CommentResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CommentCall
)
