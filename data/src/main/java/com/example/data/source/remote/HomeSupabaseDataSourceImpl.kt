package com.example.data.source.remote

import com.example.data.model.response.HomeDto
import com.example.data.model.response.PostItemDto
import com.example.data.model.response.TopicDto
import com.example.data.model.response.UserDto
import com.example.data.util.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class HomeSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    ) : HomeRemoteDataSource{
    override suspend fun getHomeDto(): HomeDto? {
        val categories = supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.getCategories,
            parameters = Json.encodeToJsonElement(
                mapOf(Constants.Supabase.Parameters.languageCode to "en")
            ) as JsonObject
        ).decodeList<PostItemDto>()

        val recentPosts = supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.getDetailedPosts,
            parameters = Json.encodeToJsonElement(
                mapOf(Constants.Supabase.Parameters.languageCode to "en")
            ) as JsonObject
        ).decodeList<PostItemDto>()

        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = "Categories",
        )

        //todo: write top interactive code
        val topInteractiveTopicDto = TopicDto(
            topicItemDtos = recentPosts,
            title = "Top Interactive",
        )
        val recentPostsTopicDto = TopicDto(
            topicItemDtos = recentPosts,
            title = "Recent Posts",
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