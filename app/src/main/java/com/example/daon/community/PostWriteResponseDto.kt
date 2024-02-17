package com.example.daon.community

import com.example.daon.community.data.PostListCall
import com.example.daon.community.data.PostOneListCall
import com.google.gson.annotations.SerializedName

data class PostWriteResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: PostOneListCall
)
