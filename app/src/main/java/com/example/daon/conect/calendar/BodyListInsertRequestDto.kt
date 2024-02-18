package com.example.daon.conect.calendar

import android.widget.EditText
import com.google.gson.annotations.SerializedName

data class BodyListInsertRequestDto(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("weight") val weight: Double,
    @SerializedName("fastingBloodSugar") val fastingBloodSugar: Int,
    @SerializedName("note") val note: String
)