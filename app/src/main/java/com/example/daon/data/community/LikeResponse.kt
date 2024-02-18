package com.example.daon.data.community

import com.example.daon.data.community.data.LikeResult
import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: LikeResult
)
