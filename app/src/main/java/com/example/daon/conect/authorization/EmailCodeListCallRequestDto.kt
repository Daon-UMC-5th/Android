package com.example.daon.conect.authorization

import com.google.gson.annotations.SerializedName

data class EmailCodeListCallRequestDto(
    @SerializedName("inputEmail") private val inputEmail: String
)
