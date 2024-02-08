package com.example.daon.conect

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DaonService {
    @POST("user/sign-up")
    fun signUp(@Body signUpRequestDto: SignUpRequestDto): Call<SignUpResponseDto>
}
