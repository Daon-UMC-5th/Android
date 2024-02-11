package com.example.daon.mypage_api

import com.google.gson.annotations.SerializedName

data class PostCommentInsertResDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
)
