package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Phone
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class AuthSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    private val profileRemoteDataSource: ProfileRemoteDataSource
) :
    AuthRemoteDataSource {
    override suspend fun signup(signupRequest: SignupRequest): AuthDto {
        supabase.auth.signUpWith(Phone) {
            phone = signupRequest.phone
            password = signupRequest.password
            data = Json.encodeToJsonElement(signupRequest) as JsonObject
        }

        val userId = supabase.auth.currentUserOrNull()?.id.orEmpty()
        Log.e("TAG", "signup: user id: $userId")

        return AuthDto(
            uuid = userId,
            name = signupRequest.name,
        )
    }

    override suspend fun login(loginRequest: LoginRequest): AuthDto {
        supabase.auth.signInWith(Phone) {
            phone = loginRequest.phone
            password = loginRequest.password
        }

        val userId = supabase.auth.currentUserOrNull()?.id.orEmpty()
        Log.e("TAG", "login: user id: $userId")

        val userProfile = profileRemoteDataSource.getCurrentUserDataById(userId)

        return AuthDto(
            uuid = userProfile?.uuid,
            image = userProfile?.image,
            name = userProfile?.name,
        )
    }

    override suspend fun verifyCode(verifyCodeRequest: VerifyCodeRequest): AuthDto {
        TODO("Not yet implemented")
    }
}