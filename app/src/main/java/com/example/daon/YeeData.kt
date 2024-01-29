package com.example.daon

data class YeeData(
    val nickname: String,
    var title: String,
    val detail: String,
    val timeAgo: String,
    val profileImage: Int,   // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
    val favorIcon: Int,     // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
    val favorCount: String,
    val commentIcon: Int,    // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
    val commentCount: String,
    val bookmarkIcon: Int,     // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
    val bookmarkCount: String
)

