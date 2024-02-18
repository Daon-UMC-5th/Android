package com.example.daon.conect.calendar

import com.google.gson.annotations.SerializedName

data class ClinicListInsertRequestDto(
    @SerializedName("hospital") val hospital : String,
    @SerializedName("content") val content : String,
    @SerializedName("alarmed_at") val alarmed_at : String,
)