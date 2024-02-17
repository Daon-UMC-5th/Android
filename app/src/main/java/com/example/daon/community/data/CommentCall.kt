package com.example.daon.community.data

import com.google.gson.annotations.SerializedName

data class CommentCall(
    @SerializedName("comment_id") val comment_id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("board_id") val board_id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("likecount") val likecount : Int
)
