package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Phone
import javax.inject.Inject

class AuthSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    private val profileRemoteDataSource: ProfileRemoteDataSource
) :
    AuthRemoteDataSource {
    override suspend fun signup(body: SignupRequest): AuthDto {
        supabase.auth.signUpWith(Phone) {
            phone = body.phone
            password = body.password
        }

        val user = supabase.auth.currentUserOrNull()
        Log.e("TAG", "signup: user id: ${user?.id}")

        return AuthDto(
            uuid = user?.id,
            name = body.name,
        )
    }

    override suspend fun login(body: LoginRequest): AuthDto {
        supabase.auth.signInWith(Phone) {
            phone = body.phone
            password = body.password
        }

        val user = supabase.auth.currentUserOrNull()

        Log.e("TAG", "login: user id: ${user?.id}")
        val userProfile = profileRemoteDataSource.getCurrentUserDataById(user?.id.orEmpty())

        return AuthDto(
            uuid = userProfile?.uuid,
            image = userProfile?.image,
            name = userProfile?.name,
            token = null,
        )
    }

    override suspend fun verifyCode(body: VerifyCodeRequest): AuthDto {
        TODO("Not yet implemented")
    }
}