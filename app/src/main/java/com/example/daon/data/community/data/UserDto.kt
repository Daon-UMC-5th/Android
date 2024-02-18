package com.example.daon.data.community.data

data class UserDto(
    val user_id: Int,
    val user_name: String,
    val email: String,
    val password: String,
    val phone_number: String,
    val birth_date: String,
    val gender: Int,
    val user_nickname: String,
    val introduction: String,
    val role: String,
    val created_at: String,
    val updated_at: String,
    val agree: String
)
