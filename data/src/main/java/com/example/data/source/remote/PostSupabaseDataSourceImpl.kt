package com.example.data.source.remote

import com.example.data.model.request.PostItemRequest
import com.example.data.model.response.PostItemDto
import com.example.data.util.Constants
import com.example.data.util.getRecentPosts
import com.example.data.util.uploadImageAndGetUrl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
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
                userId = supabase.auth.currentUserOrNull()?.id,
                contactNumber = supabase.auth.currentUserOrNull()?.phone
            )
        )//.decodeSingle<PostItemRequest>().id.orEmpty()
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
        val newImageUrl = imageByteArray?.let {
            supabase.uploadImageAndGetUrl(
                bucketId = Constants.Supabase.Buckets.postImages,
                imageByteArray = it
            ).imageUrl
        }

        return supabase.from(Constants.Supabase.Tables.posts).update(
            {
                set(Constants.Supabase.Columns.name, name)
                set(Constants.Supabase.Columns.place, place)
                set(Constants.Supabase.Columns.details, details)
                set(Constants.Supabase.Columns.categoryId, categoryId)
                set(Constants.Supabase.Columns.favoriteCategoryIds, favoriteCategoryIds)
                set(Constants.Supabase.Columns.isActive, status == "Open")
                newImageUrl?.let { set(Constants.Supabase.Columns.imageUrl, it) }
            }
        ) {
            filter {
                eq(Constants.Supabase.Columns.id, postId)
            }
        }
    }

    override suspend fun deletePost(postId: String): Any {
        return supabase.postgrest.from(Constants.Supabase.Tables.posts).delete {
            filter {
                eq(Constants.Supabase.Columns.id, postId)
            }
        } //todo: return id
    }

}