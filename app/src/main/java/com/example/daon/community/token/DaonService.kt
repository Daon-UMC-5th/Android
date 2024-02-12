package com.example.daon.community

import com.example.daon.community.SignUpRequestDto
import com.example.daon.community.SignUpResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DaonService {
    //회원가입
    @POST("user/sign-up")
    fun signUp(@Body signUpRequestDto: SignUpRequestDto): Call<SignUpResponseDto>
}
