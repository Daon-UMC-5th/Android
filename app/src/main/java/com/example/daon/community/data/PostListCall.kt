package com.example.daon.community.data

import com.google.gson.annotations.SerializedName

data class PostListCall(
    @SerializedName("board_id") val board_id: Int,
    @SerializedName("board_type") val board_type: String,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("image_url") val image_url : String,
    @SerializedName("likecount") val likecount : Int,
    @SerializedName("commentcount") val commentcount : Int,
    @SerializedName("scrapecount") val scrapecount : Int
)
