package com.example.daon.conect.authorization

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class SmsCodeBodyRequestDto(
    @SerializedName("inputCode") private val inputCode: String,
    @Query("queryPhone") private val queryPhone: String
)
