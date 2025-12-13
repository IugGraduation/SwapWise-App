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

    override suspend fun getCurrentUserPosts(languageCode: String): List<PostItemDto>? {
        val currentUserId = supabase.auth.currentUserOrNull()?.id ?: return emptyList()

        return supabase.getRecentPosts(languageCode = languageCode, showActiveOnly = false) {
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
        val user = supabase.auth.currentUserOrNull()
        if (user?.id.isNullOrBlank()) return false

        // First, update the auth table if the phone number has changed
        if (phone != user.phone) {
            supabase.auth.updateUser {
                this.phone = phone
            }
        }

        val newImageUrl = imageByteArray?.let {
            val uploadedImage = supabase.uploadAndGetPublicUrl(
                bucketId = Constants.Supabase.Buckets.userImages,
                imagePath = "${user.id}.jpg",
                imageByteArray = it
            )
            // Appending a unique timestamp is a "cache-busting" technique.
            // It forces the image loading library (Coil) to treat this as a new URL and
            // re-download the image, rather than showing a stale version from its cache.
            "${uploadedImage.imageUrl}?t=${System.currentTimeMillis()}"
        }

        // Then, update the public users table
        supabase.from(Constants.Supabase.Tables.users).update({
            set(Constants.Supabase.Columns.name, name)
            set(Constants.Supabase.Columns.phone, phone)
            set(Constants.Supabase.Columns.place, place)
            set(Constants.Supabase.Columns.bio, bio)
            newImageUrl?.let { set(Constants.Supabase.Columns.imageUrl, it) }
        }) {
            filter {
                eq(Constants.Supabase.Columns.id, user.id)
            }
        }
        return true
    }

    override suspend fun resetPassword(request: ResetPasswordRequest) {
        supabase.auth.updateUser {
            password = request.newPassword
        }
    }

}
