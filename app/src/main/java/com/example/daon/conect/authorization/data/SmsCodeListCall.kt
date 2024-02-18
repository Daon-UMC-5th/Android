package com.example.daon.conect.authorization.data

import com.google.gson.annotations.SerializedName

data class SmsCodeListCall(
    @SerializedName("groupId") val groupId: String,
    @SerializedName("to") val to: String,
    @SerializedName("from") val from: String,
    @SerializedName("type") val type: String,
    @SerializedName("statusMessage") val statusMessage: String,
    @SerializedName("country") val country: String,
    @SerializedName("messageId") val messageId: String,
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("accountId") val accountId: String,
    )
