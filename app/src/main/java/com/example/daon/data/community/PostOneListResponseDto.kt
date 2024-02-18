package com.example.daon.data.community

import com.example.daon.data.community.data.PostOneListCall
import com.google.gson.annotations.SerializedName

data class PostOneListResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PostOneListCall
)
