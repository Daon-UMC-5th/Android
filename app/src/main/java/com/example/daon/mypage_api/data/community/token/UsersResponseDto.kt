package com.example.daon.mypage_api.data.community.token

import com.example.daon.mypage_api.data.community.data.UserDto

data class UsersResponseDto(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<UserDto>
)
