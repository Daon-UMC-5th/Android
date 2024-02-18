package com.example.daon.conect.authorization.data

import com.google.gson.annotations.SerializedName

data class EmailCodeListCall(
    @SerializedName("accepted") val accepted: String,
    @SerializedName("elho") val elho: List<String>,
    @SerializedName("envelopeTime") val envelopeTime: Int,
    @SerializedName("messageTime") val messageTime: Int,
    @SerializedName("response") val response: Int,
    @SerializedName("envelope") val envelope: EnvelopeListCall
)
