package com.example.daon.calendar

import com.google.gson.annotations.SerializedName

data class ProfilechangeRequestDto(
    @SerializedName("user_nickname") val user_nickname: String,
    @SerializedName("introduction") val introduction : String
)
