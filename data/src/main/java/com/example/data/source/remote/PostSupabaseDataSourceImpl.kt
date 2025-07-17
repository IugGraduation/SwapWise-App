package com.example.data.source.remote

import com.example.data.model.request.PostItemRequest
import com.example.data.model.response.PostItemDto
import com.example.data.util.Constants
import com.example.data.util.getRecentPosts
import com.example.data.util.uploadImageAndGetUrl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class PostSupabaseDataSourceImpl @Inject constructor(private val supabase: SupabaseClient) :
    PostRemoteDataSource {
    override suspend fun getPostDetails(postId: String): PostItemDto {
        return supabase.getRecentPosts {
            filter {
                eq(Constants.Supabase.Columns.id, postId)
            }
        }.first()
    }

    override suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any {
        val imageDto = supabase.uploadImageAndGetUrl(
            bucketId = Constants.Supabase.Buckets.postImages,
            imageByteArray = imageByteArray
        )

        val postId = supabase.from(Constants.Supabase.Tables.posts).insert(
            PostItemRequest(
                name = name,
                place = place,
                details = details,
                imageUrl = imageDto.imageUrl,
                categoryId = categoryId,
                favoriteCategoryIds = favoriteCategoryIds,
                userId = supabase.auth.currentUserOrNull()?.id
            )
        ).decodeSingle<PostItemRequest>().id.orEmpty()

        return postId
    }


    override suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        place: String,
        details: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String): Any {
        TODO("Not yet implemented")
    }


}