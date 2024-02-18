package com.example.daon.conect.calendar

import com.google.gson.annotations.SerializedName

data class ClinicListInsertResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
)
