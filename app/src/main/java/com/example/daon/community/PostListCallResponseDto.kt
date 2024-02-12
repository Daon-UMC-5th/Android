package com.example.daon.community

import com.example.daon.community.data.PostListCall
import com.google.gson.annotations.SerializedName

data class PostListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PostListCall
)
