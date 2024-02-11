package com.example.daon.conect.calendar

import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.conect.calendar.data.ClinicListCall
import com.google.gson.annotations.SerializedName

data class ClinicListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ClinicListCall
)
