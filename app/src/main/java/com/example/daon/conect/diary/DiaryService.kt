package com.example.daon.conect.diary

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryService {
    @GET("/diary/get-private")
    fun diaryGetPrivate(@Header("api-key") jwtToken:String) : Call<DiaryGetResponseDto>
    @GET("/diary/get-public")
    fun diaryGetPublic(@Header("api-key") jwtToken:String) : Call<DiaryGetResponseDto>
    @GET("/diary/get-image/{year}/{month}")
    fun diaryGetImage(@Header("api-key") jwtToken: String,@Path("year") year:Int,@Path("month") month:Int) : Call<DiaryImageGetResponseDto>
    @GET("/diary/get-one-diary/{diaryDate}")
    fun diaryGetOne(@Header("api-key") jwtToken: String,@Path("diaryDate") diaryDate: String) : Call<DiaryGetOneResponseDto>
    @POST("/diary/write/post/{diaryDate}")
    fun diaryWrite(@Header("api-key") jwtToken: String,@Path("diaryDate") diaryDate: String,@Body request: DiaryWriteRequestDto) : Call<DiaryWriteResponseDto>
}