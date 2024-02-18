package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class PhoneNumCheckRequestDto(
    @SerializedName("phone_number") private val phone_number: String
)
