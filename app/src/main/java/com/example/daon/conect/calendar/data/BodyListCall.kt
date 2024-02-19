package com.example.daon.conect.calendar.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BodyListCall(
    @SerializedName("physical_record_id") val physical_record_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("alarmed_date") val alarmed_date : String,
    @SerializedName("temperature") val temperature : String,
    @SerializedName("weight") val weight : String,
    @SerializedName("fasting_blood_sugar") val fasting_blood_sugar : Int,
    @SerializedName("special_note") val special_note : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
):Serializable
