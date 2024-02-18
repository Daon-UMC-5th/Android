package com.example.daon.conect.user

import com.example.daon.conect.user.data.UsersListCall
import com.google.gson.annotations.SerializedName

data class UsersListCallResponseDto (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: UsersListCall
)