package com.example.daon.data.community.token

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DaonService {
    //회원가입
    @POST("user/sign-up")
    fun signUp(@Body signUpRequestDto: SignUpRequestDto): Call<SignUpResponseDto>
    @POST("user/login")
    fun login(@Body signUpRequestDto: LoginRequestDto): Call<LoginResponseDto>
    @GET("user/users")
    fun getAllUsers(): Call<UsersResponseDto>
}
