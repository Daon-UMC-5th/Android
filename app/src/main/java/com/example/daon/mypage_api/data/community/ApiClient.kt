package com.example.daon.mypage_api.data.community

import com.example.daon.BuildConfig
import com.example.daon.mypage_api.MypageService
import com.example.daon.mypage_api.data.community.token.LoggingInterceptor
import com.example.daon.mypage_api.data.community.token.MyApplication
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object ApiClient {

    var gson = GsonBuilder().setLenient().create()

    fun okHttpClient(interceptor: com.example.daon.mypage_api.data.community.ApiClient.AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(interceptor)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(com.example.daon.mypage_api.data.community.ApiClient.gson))
            .client(com.example.daon.mypage_api.data.community.ApiClient.okHttpClient(com.example.daon.mypage_api.data.community.ApiClient.AppInterceptor()))
            .build()
    }
    val boardService:BoardService by lazy {
        retrofit.create(BoardService::class.java)
    }
    val Mypageservice: MypageService by lazy {
        ApiClient.retrofit.create(MypageService::class.java)
    }
    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val accessToken = MyApplication.prefs.getString("token", "")
            val newRequest = request().newBuilder()
                .addHeader("api-key", accessToken)
                .build()
            proceed(newRequest)
        }
    }
}
