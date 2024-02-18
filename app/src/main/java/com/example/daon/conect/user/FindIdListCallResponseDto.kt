package com.example.daon.conect.user

import com.example.daon.conect.user.data.FindIdListCall
import com.google.gson.annotations.SerializedName

data class FindIdListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: FindIdListCall
)
