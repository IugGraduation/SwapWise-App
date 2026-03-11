package com.sam.data.source.remote

import com.sam.data.model.request.ResetPasswordRequest
import com.sam.data.model.response.PostItemDto
import com.sam.data.model.response.profile.ProfileDto

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