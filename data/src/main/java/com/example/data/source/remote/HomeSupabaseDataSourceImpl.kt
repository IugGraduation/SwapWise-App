package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicDto
import com.example.data.model.response.UserDto
import com.example.data.util.Constants
import com.example.data.util.getCategories
import com.example.data.util.getRecentPosts
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class HomeSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient
) : HomeRemoteDataSource {
    override suspend fun getHomeDto(languageCode: String): HomeDto? {
        val categories = supabase.getCategories(languageCode)

        val recentPosts = supabase.getRecentPosts(languageCode) {
            limit(20)
        }

        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = "Categories",
            url = "Categories",
        )

        //todo: write better logic for top interactive
        val topInteractiveTopicDto = TopicDto(
            topicItemDtos = recentPosts.reversed().take(10),
            title = "Top Interactive",
            url = "Top Interactive",
        )
        val recentPostsTopicDto = TopicDto(
            topicItemDtos = recentPosts.take(10),
            title = "Recent Posts",
            url = "Recent Posts",
        )

        val user = supabase.from(Constants.Supabase.Tables.users)
            .select(
                columns = Columns.list(
                    Constants.Supabase.Columns.name,
                    Constants.Supabase.Columns.imageUrl
                )
            ) {
                filter {
                    supabase.auth.currentUserOrNull()
                        ?.let { eq(Constants.Supabase.Columns.id, it.id) }
                }
            }.decodeSingle<UserDto>()
        return HomeDto(
            topicDtos = listOf(categoriesTopicDto, topInteractiveTopicDto, recentPostsTopicDto),
            userDto = user
        )
    }


    override suspend fun seeAll(languageCode: String, type: String): List<PostItemDto>? {
        return if (type == "Categories") {
            supabase.getCategories(languageCode)
        } else {
            supabase.getRecentPosts(languageCode)
        }
    }

    override suspend fun getPostsFromCategory(languageCode: String, categoryId: String): List<PostItemDto>? {
        return supabase.getRecentPosts(languageCode) {
            filter {
                eq(Constants.Supabase.Columns.categoryId, categoryId)
            }
        }
    }


}