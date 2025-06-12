package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.ProfileDto
import com.example.data.model.response.profile.ProfilePostItemDto

interface ProfileRemoteDataSource {

    suspend fun getCurrentUserDataById(id: String): ProfileDto?

    suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>

    suspend fun updateUserInfo(
        name: String,
        mobile: String,
        place: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean

    suspend fun resetPassword(request: ResetPasswordRequest)
}