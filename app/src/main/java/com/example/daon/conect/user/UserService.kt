package com.example.daon.conect.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {
    //회원가입
    @POST("user/sign-up")
    fun signUp(@Body signUpRequestDto: SignUpRequestDto): Call<SignUpResponseDto>
    //모든 유저 조회
    @GET("user/users")
    fun usersListCall(@Header("api-key") jwtToken: String): Call<UsersListCallResponseDto>
    //아이디 찾기
    @POST("user/find-id")
    fun findId(@Body findIdRequestDto: FindIdListCallRequestDto): Call<FindIdListCallResponseDto>
    //비밀번호 변경
    @PUT("user/find-pw")
    fun findPw(@Body findPwRequestDto: FindPwRequestDto): Call<FindPwResponseDto>
    //닉네임 중복 확인
    @POST("user/overlap-nickname")
    fun overlapNickname(@Body overlapNicknameRequestDto: OverlapNicknameRequestDto): Call<OverlapNicknameResponseDto>
    //로그인 jwt 생성
    @POST("user/login")
    fun login(@Body loginRequestDto: LoginRequestDto): Call<LoginResponseDto>
    //email 중복 확인
    @POST("user/email-check")
    fun emailCheck(@Body emailCheckRequestDto: EmailCheckRequestDto): Call<EmailCheckResponseDto>
    //전화번호 중복 확인
    @POST("user/phone-num-check")
    fun phoneNumCheck(@Body phoneNumCheckRequestDto: PhoneNumCheckRequestDto): Call<PhoneNumCheckResponseDto>
    //logout
    @GET("user/logout")
    fun logout(@Header("api-key") jwtToken: String): Call<LogoutResponseDto>
    //회원 탈퇴
    @DELETE("user/user-delete")
    fun userDelete(): Call<UserDeleteResponseDto>
}
