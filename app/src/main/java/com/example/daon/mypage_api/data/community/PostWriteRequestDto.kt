package com.example.daon.mypage_api.data.community

import com.google.gson.annotations.SerializedName

data class PostWriteRequestDto(
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("image_url") val image_url : String
)