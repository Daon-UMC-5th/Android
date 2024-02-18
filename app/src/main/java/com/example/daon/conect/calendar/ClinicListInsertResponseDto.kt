package com.example.daon.conect.calendar

import android.text.Editable
import com.example.daon.conect.calendar.data.ClinicListCall
import com.google.gson.annotations.SerializedName

data class ClinicListInsertResponseDto(
    @SerializedName("isSuccess") val isSuccess: Editable,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ClinicListCall
)
