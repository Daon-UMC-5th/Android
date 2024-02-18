package com.example.daon.conect.calendar

import com.example.daon.conect.calendar.data.DoseListCall
import com.google.gson.annotations.SerializedName

data class DoseAllListCallResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DoseListCall
)
