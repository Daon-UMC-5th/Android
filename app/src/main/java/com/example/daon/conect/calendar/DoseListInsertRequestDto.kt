package com.example.daon.conect.calendar

import com.google.gson.annotations.SerializedName

data class DoseListInsertRequestDto(
    @SerializedName("medicine") val medicine : String,
    @SerializedName("alarmed_at") val alarmed_at : String,
    @SerializedName("alarm_days") val alarm_days : String
)