package com.example.daon.data.community

import com.google.gson.annotations.SerializedName

data class ScrapeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
