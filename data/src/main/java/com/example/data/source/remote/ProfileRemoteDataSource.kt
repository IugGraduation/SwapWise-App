package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.Item
import com.example.data.model.response.profile.ProfilePostItemDto
import java.net.URI

interface ProfileRemoteDataSource {

    suspend fun getCurrentUserDataById(id: String): Item?

    suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>

    suspend fun updateUserInfo(
        image: URI?,
        name: String,
        mobile: String,
        bio: String,
        place: String,
    ): Boolean

    suspend fun resetPassword(request: ResetPasswordRequest)
}