package com.example.daon.conect

import android.util.Log
import com.example.daon.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private val retrofit: Retrofit
    val testService: DaonService

    init {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "INFO -> $message")
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        testService = retrofit.create(DaonService::class.java)
    }

    companion object {
        private val instance: ApiClient by lazy { ApiClient() }

        @JvmStatic
        internal fun <T> create(serviceClass: Class<T>): T {
            return instance.retrofit.create(serviceClass)
        }
    }
}