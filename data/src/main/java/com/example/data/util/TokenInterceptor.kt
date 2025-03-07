package com.example.data.util

import com.example.data.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val tokenRepository: TokenRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenRepository.getToken() }
        val request = chain.request().newBuilder().header("Authorization", "Bearer $token").build()

        return chain.proceed(request)
    }
}