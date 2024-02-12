package com.example.daon.conect

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.Date

interface DaonService {
    //회원가입
    @POST("user/sign-up")
    fun signUp(@Body signUpRequestDto: SignUpRequestDto): Call<SignUpResponseDto>
}
