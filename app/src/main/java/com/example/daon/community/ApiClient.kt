package com.example.daon.community

import com.example.daon.BuildConfig
import com.example.daon.community.token.LoggingInterceptor
import com.example.daon.community.token.MyApplication
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

    fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
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
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient(AppInterceptor()))
            .build()
    }
    val boardService: BoardService by lazy {
        retrofit.create(BoardService::class.java)
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
