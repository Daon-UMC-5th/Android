package com.example.daon.conect.authorization.data

import com.google.gson.annotations.SerializedName

data class EnvelopeListCall(
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("messageId") val messageId: String
)
