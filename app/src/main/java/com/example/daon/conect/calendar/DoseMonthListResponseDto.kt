package com.example.daon.conect.calendar

import com.example.daon.conect.calendar.data.Month
import com.google.gson.annotations.SerializedName

data class DoseMonthListResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Month>,
)
