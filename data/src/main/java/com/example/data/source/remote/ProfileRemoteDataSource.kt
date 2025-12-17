package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.profile.ProfileDto

interface ProfileRemoteDataSource {
    suspend fun getCurrentUserDataById(id: String): ProfileDto?

    suspend fun getCurrentUserPosts(languageCode: String): List<PostItemDto>?

    suspend fun updateUserInfo(
        name: String,
        phone: String,
        locationId: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean

    suspend fun resetPassword(request: ResetPasswordRequest)
}