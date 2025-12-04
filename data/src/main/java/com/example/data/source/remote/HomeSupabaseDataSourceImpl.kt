package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicDto
import com.example.data.model.response.UserDto
import com.example.data.repository.UserRepository
import com.example.data.util.Constants
import com.example.data.util.getCategories
import com.example.data.util.getRecentPosts
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class HomeSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    private val userRepository: UserRepository
) : HomeRemoteDataSource {
    override suspend fun getHomeDto(): HomeDto? {
        val lang = userRepository.getLatestSelectedAppLanguage().first()
        val categories = supabase.getCategories(lang)

        val recentPosts = supabase.getRecentPosts(lang) {
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


    override suspend fun seeAll(type: String): List<PostItemDto>? {
        val lang = userRepository.getLatestSelectedAppLanguage().first()
        return if (type == "Categories") {
            supabase.getCategories(lang)
        } else {
            supabase.getRecentPosts(lang)
        }
    }

    override suspend fun getPostsFromCategory(categoryId: String): List<PostItemDto>? {
        val lang = userRepository.getLatestSelectedAppLanguage().first()
        return supabase.getRecentPosts(lang) {
            filter {
                eq(Constants.Supabase.Columns.categoryId, categoryId)
            }
        }
    }


}