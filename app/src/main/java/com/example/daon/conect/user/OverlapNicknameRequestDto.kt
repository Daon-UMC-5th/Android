package com.example.daon.conect.user

import com.google.gson.annotations.SerializedName

data class OverlapNicknameRequestDto(
    @SerializedName("user_nickname") private val user_nickname: String
)
