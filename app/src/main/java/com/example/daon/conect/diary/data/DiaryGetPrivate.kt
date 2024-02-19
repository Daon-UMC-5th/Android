package com.example.daon.conect.diary.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DiaryGetPrivate(
    @SerializedName("diary_id") val diaryId : Int,
    @SerializedName("user_id") val userId : Int,
    @SerializedName("user_nickname") val userNickname : String,
    @SerializedName("is_private") val isPrivate: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val createdAt : String,
    @SerializedName("updated_at") val updatedAt : String,
    @SerializedName("itemCount") val itemCount : Int,
    @SerializedName("totalResults") val totalResult : Int,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("likes_count") val likesCount : Int,
):Serializable