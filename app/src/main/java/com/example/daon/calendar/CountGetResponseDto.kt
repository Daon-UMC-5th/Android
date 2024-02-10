package com.example.daon.calendar

import com.example.daon.calendar.data.ResultItem
import com.google.gson.annotations.SerializedName

data class CountGetResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code")val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<ResultItem>
)


