package com.example.daon.conect.calendar.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoseListCall(
    @SerializedName("medication_id") val medicationId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("alarmed_date") val alarmedDate: String,
    @SerializedName("time_of_day") val timeOfDay: String,
    @SerializedName("medicine") val medicine: String,
    @SerializedName("alarmed_at") val alarmedAt: String,
    @SerializedName("alarm_days") val alarmDays: String,
    @SerializedName("repeat_status") val repeatStatus: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
):Serializable