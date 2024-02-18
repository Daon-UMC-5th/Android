package com.example.daon.mypage_api

import com.example.daon.conect.calendar.data.PostComment
import com.google.gson.annotations.SerializedName

data class PostBoardInsertResDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PostComment
)