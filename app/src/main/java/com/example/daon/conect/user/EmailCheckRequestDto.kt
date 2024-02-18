package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class EmailCheckRequestDto(
    @SerializedName("email") private val email: String
)
