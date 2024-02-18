package com.example.daon.conect.calendar.data

import com.google.gson.annotations.SerializedName

data class DoseListCall(
    @SerializedName("medication_id") val medication_id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("alarmed_date") val alarmed_date : String,
    @SerializedName("time_of_day") val time_of_day : String,
    @SerializedName("medicine") val medicine : String,
    @SerializedName("alarmed_at") val alarmed_at : String,
    @SerializedName("repeat_status") val repeat_status : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)
