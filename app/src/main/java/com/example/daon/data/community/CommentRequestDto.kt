package com.example.daon.data.community

import com.google.gson.annotations.SerializedName

data class CommentRequestDto (
    @SerializedName("content") val content : String
)
