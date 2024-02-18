package com.example.daon.conect.user.data

import com.google.gson.annotations.SerializedName

data class UsersListCall (
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("birth_date") val birth_date: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("user_nickname") val user_nickname: String,
    @SerializedName("introduction") val introduction: String,
    @SerializedName("role") val role: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("agree") val agree: String
)