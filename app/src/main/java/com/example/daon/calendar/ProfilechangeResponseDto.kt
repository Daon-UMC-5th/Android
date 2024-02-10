package com.example.daon.conect.calendar

import com.google.gson.annotations.SerializedName

data class ProfilechangeResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)