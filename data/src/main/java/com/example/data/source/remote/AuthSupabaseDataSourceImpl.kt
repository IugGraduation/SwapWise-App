package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Phone

class AuthSupabaseDataSourceImpl(
    private val supabaseClient: SupabaseClient,
    private val profileRemoteDataSource: ProfileRemoteDataSource
) :
    AuthRemoteDataSource {
    override suspend fun signup(body: SignupRequest): AuthDto {
        val userInfo = supabaseClient.auth.signUpWith(Phone) {
            phone = body.phone
            password = body.password
        }

        Log.e("TAG", "signup: user id: ${userInfo?.id}")
        val user = profileRemoteDataSource.getCurrentUserDataById(userInfo?.id.orEmpty())

        return AuthDto(
            uuid = user?.uuid,
            image = user?.image,
            name = user?.name,
            token = null,
        )
    }

    override suspend fun login(body: LoginRequest): AuthDto {
        supabaseClient.auth.signInWith(Phone) {
            phone = body.phone
            password = body.password
        }

        val userInfo = supabaseClient.auth.currentUserOrNull()

        Log.e("TAG", "login: user id: ${userInfo?.id}")
        val user = profileRemoteDataSource.getCurrentUserDataById(userInfo?.id.orEmpty())

        return AuthDto(
            uuid = user?.uuid,
            image = user?.image,
            name = user?.name,
            token = null,
        )
    }

    override suspend fun verifyCode(body: VerifyCodeRequest): AuthDto {
        TODO("Not yet implemented")
    }
}