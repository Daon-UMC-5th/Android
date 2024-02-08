package com.example.daon.conect.calendar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.Date

interface CalendarService {
    //캘린더
    //신체 조회
    @GET("calendar/physical-record/{date}")
    fun bodyListCall(@Path("date") date: Date): Call<BodyListCallResponseDto>
    //신체 삽입
    @POST("calendar/physical-record/{date}")
    fun bodyListInsert(@Path("date") date: Date, @Body request: BodyListInsertRequestDto): Call<BodyListInsertResponseDto>
    //신체 삭제
    @DELETE("calendar/physical-record/{date}")
    fun bodyListDelete(@Path("date") date: Date) : Call<BodyListInsertResponseDto>
    //신체 수정
    @PUT("calendar/physical-record/{date}")
    fun bodyListEdit(@Path("date") date: Date, @Body request: BodyListInsertRequestDto) : Call<BodyListInsertResponseDto>
    //진료 조회
    @GET("calendar/consultation/{date}")
    fun clinicListCall(@Path("date") date: Date) : Call<ClinicListCallResponseDto>
    //진료 삽입
    @POST("calendar/consultation/{date}")
    fun clinicListInsert(@Path("date") date: Date, @Body request: ClinicListInsertRequestDto) : Call<ClinicListInsertResponseDto>
    //진료 삭제
    @DELETE("calendar/consultation/{date}")
    fun clinicListDelete(@Path("date") date: Date) : Call<ClinicListInsertResponseDto>
    //진료 수정
    @PUT("calendar/consultation/{date}")
    fun clinicListEdit(@Path("date") date: Date, @Body request: ClinicListInsertRequestDto) : Call<ClinicListInsertResponseDto>
}