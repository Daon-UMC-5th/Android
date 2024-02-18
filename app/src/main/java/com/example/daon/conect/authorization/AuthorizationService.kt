package com.example.daon.conect.authorization

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {
    //이메일 - 인증코드 전송
    @POST("auth/email/code")
    fun emailCode(@Body emailCodeRequestDto: EmailCodeListCallRequestDto): Call<EmailCodeListCallResponseDto>
    //이메일 - 인증코드 확인
    @POST("auth/email/code/check")
    fun emailCodeCheck(@Body emailCodeCheckRequestDto: EmailCodeCheckRequestDto): Call<EmailCodeCheckResponseDto>
    //문자 - 인증코드 전송
    @POST("auth/sms/code")
    fun smsCode(@Body smsCodeRequestDto: SmsCodeListCallRequestDto): Call<SmsCodeListCallResponseDto>
    //문자 - 인증코드 확인
    @POST("auth/sms/code/check")
    fun smsCodeCheck(@Body smsCodeBodyRequestDto: SmsCodeBodyRequestDto): Call<SmsCodeBodyResponseDto>
}