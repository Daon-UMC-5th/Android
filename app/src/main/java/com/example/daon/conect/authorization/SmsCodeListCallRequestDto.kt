package com.example.daon.conect.authorization

import com.google.gson.annotations.SerializedName

data class SmsCodeListCallRequestDto(
    @SerializedName("inputPhone") private val inputPhone: String
)
