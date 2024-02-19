package com.example.daon.conect

import android.util.Log
import com.example.daon.conect.diary.DiaryService
import com.google.gson.GsonBuilder
import com.prolificinteractive.materialcalendarview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    var gson = GsonBuilder().setLenient().create()
    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            // 아래 라인은 안전하지 않은 HTTP를 허용합니다.
            .hostnameVerifier { _, _ -> true }
            .build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(com.example.daon.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val diaryService: DiaryService by lazy {
        retrofit.create(DiaryService::class.java)
    }
}