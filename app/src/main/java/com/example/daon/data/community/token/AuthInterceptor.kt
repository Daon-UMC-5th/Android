package com.example.daon.data.community.token

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requeset = chain.request().newBuilder()
            .addHeader("Authorization", MyApplication.prefs.getToken() ?: "")
            .build()

        return chain.proceed(requeset)
    }
}