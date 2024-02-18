package com.example.daon.conect.user.data

import com.google.gson.annotations.SerializedName

data class FindIdListCall(
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val created_at: String
)
