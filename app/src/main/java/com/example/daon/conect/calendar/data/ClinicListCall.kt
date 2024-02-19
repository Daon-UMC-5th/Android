package com.example.daon.conect.calendar.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ClinicListCall(
    @SerializedName("consultation_id") val consultation_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("hospital") val hospital : String,
    @SerializedName("content") val content : String,
    @SerializedName("alarmed_date") val alarmed_date : String,
    @SerializedName("alarmed_at") val alarmed_at : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
):Serializable