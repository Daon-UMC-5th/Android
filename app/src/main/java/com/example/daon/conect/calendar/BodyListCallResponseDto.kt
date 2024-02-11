package com.example.daon.conect.calendar

import com.example.daon.conect.calendar.data.BodyListCall
import com.google.gson.annotations.SerializedName

data class BodyListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: BodyListCall
)