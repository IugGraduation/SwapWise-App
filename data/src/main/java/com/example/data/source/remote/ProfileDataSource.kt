package com.example.data.source.remote

import com.example.data.model.response.profile.ProfileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProfileDataSource {

    @GET("profile/{user_id}")
    suspend fun getCurrentUserDataById(
        @Path("user_id") id: String,
        @Header("Authorization") token: String
    ): Response<ProfileDto>
}