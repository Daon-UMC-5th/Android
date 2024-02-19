package com.example.daon.conect.diary

import com.example.daon.conect.diary.data.DiaryGetImage
import com.google.gson.annotations.SerializedName

data class DiaryImageGetResponseDto(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<DiaryGetImage>
)