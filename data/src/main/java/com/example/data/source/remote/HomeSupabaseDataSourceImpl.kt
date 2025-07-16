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
import io.github.jan.supabase.postgrest.query.request.RpcRequestBuilder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class HomeSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    ) : HomeRemoteDataSource{
    override suspend fun getHomeDto(): HomeDto? {
        val categories = getCategories()

        val recentPosts = getRecentPosts()

        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = "Categories",
            url = "Categories",
        )

        //todo: write top interactive code
        val topInteractiveTopicDto = TopicDto(
            topicItemDtos = recentPosts,
            title = "Top Interactive",
            url = "Top Interactive",
        )
        val recentPostsTopicDto = TopicDto(
            topicItemDtos = recentPosts,
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
            topicDtos = listOf(categoriesTopicDto, topInteractiveTopicDto,recentPostsTopicDto) ,
            userDto = user
        )
    }

    private suspend fun getCategories(): List<PostItemDto> {
        return supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.getCategories,
            parameters = Json.encodeToJsonElement(
                //todo: get language from user info, same for anywhere with "en"
                mapOf(Constants.Supabase.Parameters.languageCode to "en")
            ) as JsonObject
        ).decodeList<PostItemDto>()
    }

    private suspend fun getRecentPosts(request: RpcRequestBuilder.() -> Unit = {}): List<PostItemDto> {
        return supabase.postgrest.rpc(
            function = Constants.Supabase.Functions.getDetailedPosts,
            parameters = Json.encodeToJsonElement(
                mapOf(Constants.Supabase.Parameters.languageCode to "en")
            ) as JsonObject,
            request = request
        ).decodeList<PostItemDto>()
    }

    override suspend fun seeAll(type: String): List<PostItemDto>? {
        return if (type == "Categories") {
            getCategories()
        } else {
            getRecentPosts()
        }
    }

    override suspend fun getPostsFromCategory(categoryId: String): List<PostItemDto>? {
        return getRecentPosts {
            filter {
                eq(Constants.Supabase.Columns.categoryId, categoryId)
            }
        }
    }


}