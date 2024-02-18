package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class FindIdListCallRequestDto(
    @SerializedName("user_name") private val user_name: String,
    @SerializedName("birth_date") private val birth_date: String,
    @SerializedName("gender") private val gender: Int,
    @SerializedName("phone_number") private val phone_number: String
)
