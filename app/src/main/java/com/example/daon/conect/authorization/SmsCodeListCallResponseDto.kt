package com.example.daon.conect.authorization

import com.example.daon.conect.authorization.data.SmsCodeListCall
import com.google.gson.annotations.SerializedName

data class SmsCodeListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: SmsCodeListCall
)
