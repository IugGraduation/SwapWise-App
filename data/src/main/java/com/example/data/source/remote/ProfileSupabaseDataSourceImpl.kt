package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.profile.ProfileDto
import com.example.data.util.Constants
import com.example.data.util.getRecentPosts
import com.example.data.util.uploadAndGetPublicUrl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class ProfileSupabaseDataSourceImpl @Inject constructor(private val supabase: SupabaseClient) :
    ProfileRemoteDataSource {
    override suspend fun getCurrentUserDataById(id: String): ProfileDto? {
        return supabase.from(Constants.Supabase.Tables.users)
            .select {
                filter {
                    eq(Constants.Supabase.Columns.id, id)
                }
            }.decodeSingle<ProfileDto>()
    }

    override suspend fun getCurrentUserPosts(): List<PostItemDto>? {
        val currentUserId = supabase.auth.currentUserOrNull()?.id ?: return emptyList()

        return supabase.getRecentPosts {
            filter {
                eq(
                    "${Constants.Supabase.Columns.user}->>${Constants.Supabase.Columns.id}",
                    currentUserId
                )
            }
        }
    }


    override suspend fun updateUserInfo(
        name: String,
        phone: String,
        place: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean {
        val userId = supabase.auth.currentUserOrNull()?.id
        if (userId.isNullOrBlank()) return false

        val newImageUrl = imageByteArray?.let {
            supabase.uploadAndGetPublicUrl(
                bucketId = Constants.Supabase.Buckets.userImages,
                imagePath = "$userId.jpg",
                imageByteArray = it
            ).imageUrl
        }

        supabase.from(Constants.Supabase.Tables.users).update({
            set(Constants.Supabase.Columns.name, name)
            set(Constants.Supabase.Columns.phone, phone)
            set(Constants.Supabase.Columns.place, place)
            set(Constants.Supabase.Columns.bio, bio)
            newImageUrl?.let { set(Constants.Supabase.Columns.imageUrl, it) }
        }) {
            filter {
                eq(Constants.Supabase.Columns.id, userId)
            }
        }
        return true
    }

    override suspend fun resetPassword(request: ResetPasswordRequest) {
        TODO("Coming Soon")
    }

}
