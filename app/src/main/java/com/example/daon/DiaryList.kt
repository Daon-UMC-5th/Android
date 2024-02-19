package com.example.daon

import android.graphics.Bitmap

data class DiaryList(
    val id : Int,
    val nickname: String,
    val time : String, //Time
    val title : String,
    val content: String,
    val image: Bitmap,
    val heart: Int
)
