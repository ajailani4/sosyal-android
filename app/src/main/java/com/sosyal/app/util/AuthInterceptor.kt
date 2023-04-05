package com.sosyal.app.util

import com.sosyal.app.domain.use_case.user_credential.GetAccessTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val accessToken = runBlocking {
            getAccessTokenUseCase().first()
        }

        requestBuilder.addHeader("Authorization", "Bearer $accessToken")

        return chain.proceed(requestBuilder.build())
    }
}