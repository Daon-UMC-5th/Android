package com.example.mypage2

data class Post(val post: String,
                val nickname: String,
                val title: String,
                val detail: String,
                val timeAgo: String,
                val profileImage: Int,
                val favorIcon: Int,     // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
                val favorCount: String,
                val commentIcon: Int,    // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
                val commentCount: String,
                val bookmarkIcon: Int,     // 이미지 리소스 ID 또는 이미지 URL 등을 저장할 수 있도록 타입을 조정
                val bookmarkCount: String)
