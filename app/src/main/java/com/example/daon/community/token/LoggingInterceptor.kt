package com.example.daon.community.token

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        // 요청 헤더를 로깅합니다.
        println("Request Headers: ${request.headers}")

        val response: Response = chain.proceed(request)
        // 응답 헤더를 로깅합니다.
        println("Response Headers: ${response.headers}")

        return response
    }
}
