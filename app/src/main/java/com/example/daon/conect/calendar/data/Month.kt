package com.example.daon.conect.calendar.data

import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("date") val date : String,
    @SerializedName("physical_record") val physical_record : Int,
    @SerializedName("consultation") val consultation : Int,
    @SerializedName("medication") val medication : Int
)