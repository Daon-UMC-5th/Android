package com.example.daon.conect.diary

import com.google.gson.annotations.SerializedName

data class DiaryWriteRequestDto(
    @SerializedName("is_private") val isPrivate : Int,
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("image_url") val imageUrl : String
)
