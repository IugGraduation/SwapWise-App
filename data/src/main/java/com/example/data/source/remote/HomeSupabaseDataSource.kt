package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostImageDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicDto
import com.example.data.model.response.TopicItemDto
import com.example.data.model.response.UserDto
import com.example.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class HomeSupabaseDataSource @Inject constructor(
    private val supabase: SupabaseClient,
    ) : HomeRemoteDataSource{
    override suspend fun getHomeDto(): HomeDto? {
        val categories = supabase.from(Constants.Supabase.categories).select().decodeList<TopicItemDto>()
        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = "Categories",
        )

        val recentPosts = supabase.postgrest.rpc(Constants.Supabase.getRecentPosts)
            .decodeList<TopicItemDto>()
        val topInteractiveTopicDto = TopicDto(
            topicItemDtos = recentPosts,
            title = "Top Interactive",
        )
        val recentPostsTopicDto = TopicDto(
            topicItemDtos = recentPosts,
            title = "Recent Posts",
        )

        val user = supabase.from(Constants.Supabase.users)
            .select(
                columns = Columns.list(UserDto::name.name, UserDto::imageUrl.name)
            ) {
                filter {
                    supabase.auth.currentUserOrNull()?.let { eq("id", it.id) }
                }
            }

            .decodeSingle<UserDto>()
        return HomeDto(
            topicDtos = listOf(categoriesTopicDto, topInteractiveTopicDto,recentPostsTopicDto) ,
            userDto = user
        )
    }

    override suspend fun seeAll(type: String): List<TopicItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsFromCategory(categoryId: String): List<TopicItemDto>? {
        TODO("Not yet implemented")
    }


}