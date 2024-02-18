package com.example.daon.mypage_api.data.community

import com.google.gson.annotations.SerializedName

data class CommentRequestDto (
    @SerializedName("content") val content : String
)
