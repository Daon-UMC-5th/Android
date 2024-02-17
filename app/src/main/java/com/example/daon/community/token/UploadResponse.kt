package com.example.daon.community.token

import com.example.daon.community.data.UploadResult
import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UploadResult
)
