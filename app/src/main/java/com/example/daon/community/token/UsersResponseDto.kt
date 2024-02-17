package com.example.daon.community.token

import com.example.daon.community.data.UserDto

data class UsersResponseDto(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<UserDto>
)
