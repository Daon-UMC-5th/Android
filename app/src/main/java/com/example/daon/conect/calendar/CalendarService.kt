package com.example.daon.conect.calendar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CalendarService {
    //캘린더
    //신체 조회
    @GET("calendar/physical-record/{date}")
    fun bodyListCall(@Header("api-key") jwtToken: String, @Path("date") date: String): Call<BodyListCallResponseDto>
    //신체 삽입
    @POST("calendar/physical-record/{date}")
    fun bodyListInsert(@Header("api-key") jwtToken: String,@Path("date") date: String, @Body request: BodyListInsertRequestDto): Call<BodyListInsertResponseDto>
    //신체 삭제
    @DELETE("calendar/physical-record/{date}")
    fun bodyListDelete(@Header("api-key") jwtToken: String,@Path("date") date: String) : Call<BodyListInsertResponseDto>
    //신체 수정
    @PUT("calendar/physical-record/{date}")
    fun bodyListEdit(@Header("api-key") jwtToken: String,@Path("date") date: String, @Body request: BodyListInsertRequestDto) : Call<BodyListInsertResponseDto>
    //진료 조회
    @GET("calendar/consultation/all/{date}")
    fun clinicListCall(@Header("api-key") jwtToken: String,@Path("date") date: String) : Call<ClinicListCallResponseDto>
    //진료 삽입
    @POST("calendar/consultation/{date}")
    fun clinicListInsert(@Header("api-key") jwtToken: String,@Path("date") date: String, @Body request: ClinicListInsertRequestDto) : Call<ClinicListInsertResponseDto>
    //진료 개별조회
    @GET("calendar/consultation/{id}")
    fun clinicOneCall(@Header("api-key") jwtToken: String,@Path("id") id: String) : Call<ClinicListCallResponseDto>
    //진료 삭제
    @DELETE("calendar/consultation/{id}")
    fun clinicListDelete(@Header("api-key") jwtToken: String,@Path("id") id: Int) : Call<ClinicListInsertResponseDto>
    //진료 수정
    @PUT("calendar/consultation/{id}")
    fun clinicListEdit(@Header("api-key") jwtToken: String,@Path("id") id: Int, @Body request: ClinicListInsertRequestDto) : Call<ClinicListInsertResponseDto>
    //복용 조회
    @GET("calendar/medication/all/{date}")
    fun doseAllListCall(@Header("api-key") jwtToken: String, @Path("date") date: String) : Call<DoseAllListCallResponseDto>
    @GET("calendar/medication/{id}")
    fun doseListCall(@Header("api-key") jwtToken: String,@Path("id") id : Int): Call<DoseListCallResponseDto>
    //복용 삽입
    @POST("calendar/medication/{when}/{date}")
    fun doseListInsert(@Header("api-key") jwtToken: String,@Path("when") time: String,@Path("date") date: String, @Body request: DoseListInsertRequestDto) : Call<DoseListInsertResponseDto>
    //복용 삭제
    @DELETE("calendar/medication/{id}")
    fun doseListDelete(@Header("api-key") jwtToken: String,@Path("id") id: Int) : Call<DoseListInsertResponseDto>
    //복용 수정
    @PUT("calendar/medication/{id}")
    fun doseListEdit(@Header("api-key") jwtToken: String,@Path("id") id: Int, @Body request: DoseListInsertRequestDto) : Call<DoseListInsertResponseDto>
    @GET("calendar/{month}")
    fun doseMonthList(@Header("api-key") jwtToken: String, @Path("month") month: String) : Call<DoseMonthListResponseDto>
}