package com.example.daon.conect.authorization

import com.example.daon.conect.authorization.data.EmailCodeListCall
import com.google.gson.annotations.SerializedName

data class EmailCodeListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: EmailCodeListCall
)
