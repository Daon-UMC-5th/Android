package com.example.daon.community.token

import com.google.gson.annotations.SerializedName

data class SignUpRequestDto(
    @SerializedName("user_name") private val user_name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone_number") private val phone_number: String,
    @SerializedName("birth_date") private val birth_date: String,
    @SerializedName("gender") private val gender: Int,
    @SerializedName("user_nickname") private val user_nickname: String,
    @SerializedName("introduction") private val introduction: String,
    @SerializedName("role") private val role: String,
    @SerializedName("agree") private val agree: String,
    @SerializedName("profile_url") private val profile_url: String,
    @SerializedName("doctor_url") private val doctor_url: String
)