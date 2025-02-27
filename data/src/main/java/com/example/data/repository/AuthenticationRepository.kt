package com.example.data.repository

import com.example.data.model.request.SignupRequest
import com.example.data.source.remote.AuthenticationRemoteDataSource
import com.example.data.util.checkResponse

class AuthenticationRepository(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
) {
    suspend fun signup(body: SignupRequest) =
        checkResponse { authenticationRemoteDataSource.signup(body) }

}