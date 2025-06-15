package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.ProfileDto
import com.example.data.model.response.profile.ProfilePostItemDto
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

class ProfileSupabaseDataSourceImpl @Inject constructor(supabaseClient: SupabaseClient) :
    ProfileRemoteDataSource {
    override suspend fun getCurrentUserDataById(id: String): ProfileDto? {

        //todo: implement the getUser fun
        return ProfileDto(
            uuid = id,
            name = "TODO()",
            image = "TODO()",
        )
    }

    override suspend fun getCurrentUserPosts(): List<ProfilePostItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserInfo(
        name: String,
        phone: String,
        place: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(request: ResetPasswordRequest) {
        TODO("Not yet implemented")
    }

}