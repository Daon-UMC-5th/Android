package com.example.daon.community

import com.google.gson.annotations.SerializedName

data class CommentRequestDto (
    @SerializedName("content") val content : String
)
