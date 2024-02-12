package com.example.daon.community

import com.google.gson.annotations.SerializedName

data class PostWriteRequestDto(
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String
)