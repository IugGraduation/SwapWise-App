package com.sam.data.source.remote

import com.sam.data.model.request.PostItemRequest
import com.sam.data.model.response.PostItemDto
import com.sam.data.util.Constants
import com.sam.data.util.getRecentPosts
import com.sam.data.util.uploadAndGetPublicUrl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import java.util.UUID
import javax.inject.Inject

class PostSupabaseDataSourceImpl @Inject constructor(private val supabase: SupabaseClient) :
    PostRemoteDataSource {
    override suspend fun getPostDetails(languageCode: String, postId: String): PostItemDto {
        return supabase.getRecentPosts(languageCode) {
            filter {
                eq(Constants.Supabase.Columns.id, postId)
            }
        }.first()
    }

    override suspend fun addPost(
        imageByteArray: ByteArray,
        name: String,
        details: String,
        locationId: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?
    ): Any {
        val postId = UUID.randomUUID().toString()
        val imageDto = supabase.uploadAndGetPublicUrl(
            bucketId = Constants.Supabase.Buckets.postImages,
            imagePath = "$postId.jpg",
            imageByteArray = imageByteArray
        )

        return supabase.from(Constants.Supabase.Tables.posts).insert(
            PostItemRequest(
                id = postId,
                name = name,
                locationId = locationId,
                details = details,
                imageUrl = imageDto.imageUrl,
                categoryId = categoryId,
                favoriteCategoryIds = favoriteCategoryIds,
                userId = supabase.auth.currentUserOrNull()?.id
            )
        )
    }


    override suspend fun updatePost(
        imageByteArray: ByteArray?,
        name: String,
        details: String,
        locationId: String,
        categoryId: String,
        favoriteCategoryIds: List<String>?,
        postId: String,
        status: String
    ): Any {
        val newImageUrl = imageByteArray?.let {
            supabase.uploadAndGetPublicUrl(
                bucketId = Constants.Supabase.Buckets.postImages,
                imagePath = "$postId.jpg",
                imageByteArray = it
            ).imageUrl
        }

        return supabase.from(Constants.Supabase.Tables.posts).update(
            {
                set(Constants.Supabase.Columns.name, name)
                set(Constants.Supabase.Columns.locationId, locationId)
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
        return supabase.from(Constants.Supabase.Tables.posts).delete {
            filter {
                eq(Constants.Supabase.Columns.id, postId)
            }
        }
    }

}
