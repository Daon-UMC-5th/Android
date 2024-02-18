package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class SignUpRequestDto(
    @SerializedName("user_name") private val user_name: String,
    @SerializedName("email") private val email: String,
    @SerializedName("password") private val password: String,
    @SerializedName("phone_number") private val phone_number: String,
    @SerializedName("birth_date") private val birth_date: String,
    @SerializedName("gender") private val gender: Int,
    @SerializedName("user_nickname") private val user_nickname: String,
    @SerializedName("introduction") private val introduction: String,
    @SerializedName("role") private val role: String,
    @SerializedName("agree") private val agree: String
)