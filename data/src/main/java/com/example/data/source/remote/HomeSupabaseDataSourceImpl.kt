package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicDto
import com.example.data.model.response.UserDto
import com.example.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class HomeSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    ) : HomeRemoteDataSource{
    override suspend fun getHomeDto(): HomeDto? {
        val categories = supabase.from(Constants.Supabase.categories).select(
            Columns.list(
                Constants.Supabase.id,
                Constants.Supabase.name,
                Constants.Supabase.imageUrl,
            )
        ).decodeList<PostItemDto>()
        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = "Categories",
        )

        val recentPosts = supabase.from(Constants.Supabase.detailedPosts).select()
            .decodeList<PostItemDto>()
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

    override suspend fun seeAll(type: String): List<PostItemDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsFromCategory(categoryId: String): List<PostItemDto>? {
        TODO("Not yet implemented")
    }


}