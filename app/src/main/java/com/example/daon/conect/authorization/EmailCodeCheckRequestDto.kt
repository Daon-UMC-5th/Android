package com.example.daon.conect.authorization

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class EmailCodeCheckRequestDto(
    @SerializedName("inputCode") private val inputCode: String,
    @Query("queryEmail") private val queryEmail: String
)
