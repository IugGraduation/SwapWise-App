package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import com.example.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.postgrest.from
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

        val userId = supabase.auth.currentUserOrNull()?.id.orEmpty()
        Log.e("TAG", "signup: user id: $userId")

        insertUserIntoSupabase(body.copy(id = userId))

        return AuthDto(
            uuid = userId,
            name = body.name,
        )
    }

    private suspend fun insertUserIntoSupabase(body: SignupRequest) {
        supabase.from(Constants.Supabase.Tables.users).insert(body)
    }

    override suspend fun login(body: LoginRequest): AuthDto {
        supabase.auth.signInWith(Phone) {
            phone = body.phone
            password = body.password
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

    override suspend fun verifyCode(body: VerifyCodeRequest): AuthDto {
        TODO("Not yet implemented")
    }
}