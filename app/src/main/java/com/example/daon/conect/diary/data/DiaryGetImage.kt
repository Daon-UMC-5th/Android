package com.example.daon.conect.diary.data

import com.google.gson.annotations.SerializedName

data class DiaryGetImage(
    @SerializedName("diary_date") val diaryDate : Int,
    @SerializedName("image_url") val imageUrl : String
)