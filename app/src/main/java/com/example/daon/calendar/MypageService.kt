package com.example.daon.calendar

import com.example.daon.conect.calendar.PostBoardResponseDto
import com.example.daon.conect.calendar.PostCommentResponseDto
import com.example.daon.conect.calendar.PostScrapResponseDto
import com.example.daon.conect.calendar.ProfilechangeResponseDto
import retrofit2.Call
import retrofit2.http.*

interface MypageService {
    //마이페이지
    //내가 쓴 게시물 가져오기
    @GET("mypage/get-board")
    fun boardget(@Header("api-key") jwtToken: String, @Query("offset") offset: Int): Call<PostBoardResponseDto>
    //내가 댓글 단 게시물 가져오기
    @GET("mypage/get-comment")
    fun commentget(@Header("api-key") jwtToken: String, @Query("offset") offset: Int): Call<PostCommentResponseDto>
    //내가 스크랩한 게시물 가져오기
    @GET("mypage/get-scrap")
    fun scrapget(@Header("api-key") jwtToken: String, @Query("offset") offset: Int): Call<PostScrapResponseDto>
    //게시물 댓글 스크랩 수 가져오기
    @GET("mypage/get-page")
    fun pageget(@Header("api-key") jwtToken: String): Call<CountGetResponseDto>
    //프로필 변경
    @PUT("mypage/change-profile")
    fun changeProfile(@Body request: ProfilechangeRequestDto): Call<ProfilechangeResponseDto>
}