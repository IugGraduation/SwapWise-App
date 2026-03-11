package com.sam.data.source.remote

import com.sam.data.model.response.HomeDto
import com.sam.data.model.response.PostItemDto
import com.sam.data.model.response.TopicDto
import com.sam.data.model.response.UserDto
import com.sam.data.util.Constants
import com.sam.data.util.getCategories
import com.sam.data.util.getRecentPosts
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

        //this should be server job, but we will use this for the time being
        val (categoriesTitle, topInteractiveTitle, recentPostsTitle) = when (languageCode) {
            "ar" -> Triple("الفئات", "الأكثر تفاعلاً", "أحدث المنشورات")
            else -> Triple("Categories", "Top Interactive", "Recent Posts")
        }

        val categoriesTopicDto = TopicDto(
            topicItemDtos = categories,
            title = categoriesTitle,
            url = "Categories",
        )

        //todo: write better logic for top interactive
        val topInteractiveTopicDto = TopicDto(
            topicItemDtos = recentPosts.reversed().take(10),
            title = topInteractiveTitle,
            url = "Top Interactive",
        )
        val recentPostsTopicDto = TopicDto(
            topicItemDtos = recentPosts.take(10),
            title = recentPostsTitle,
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
