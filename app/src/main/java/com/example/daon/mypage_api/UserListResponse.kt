package com.example.daon.mypage_api

import com.example.daon.mypage_api.data.User
import com.google.gson.annotations.SerializedName

data class UserListResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: User
)