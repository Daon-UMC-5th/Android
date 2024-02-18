package com.example.daon.conect.calendar

import com.google.gson.annotations.SerializedName

data class BodyListInsertRequestDto(
    @SerializedName("temperature") val temperature : Double,
    @SerializedName("weight") val weight : Int,
    @SerializedName("fastingBloodSugar") val fastingBloodSugar : Int,
    @SerializedName("note") val note : String
)