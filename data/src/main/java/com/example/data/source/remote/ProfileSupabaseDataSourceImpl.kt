package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.ImageDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.profile.ProfileDto
import com.example.data.util.Constants
import com.example.data.util.getRecentPosts
import com.example.data.util.updateImageAndGetUrl
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
                    supabase.auth.currentUserOrNull()
                        ?.let { eq(Constants.Supabase.Columns.id, it.id) }
                }
            }.decodeSingle<ProfileDto>()
    }

    override suspend fun getCurrentUserPosts(): List<PostItemDto>? {
        return supabase.getRecentPosts {
            filter {
                supabase.auth.currentUserOrNull()
                    ?.let { eq(Constants.Supabase.Columns.id, it.id) }
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
        var imageDto: ImageDto? = null
        imageByteArray?.let {
            imageDto = supabase.updateImageAndGetUrl(
                bucketId = Constants.Supabase.Buckets.userImages,
                imageByteArray = it
            )
        }
        supabase.from(Constants.Supabase.Tables.users).update({

        })
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(request: ResetPasswordRequest) {
        TODO("Coming Soon")
    }

}