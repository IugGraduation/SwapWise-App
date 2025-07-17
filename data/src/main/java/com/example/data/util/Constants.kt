package com.example.data.util

object Constants {
    const val TOKEN = "token"

    object Supabase {
        object Tables {
            const val users = "users"
            const val posts = "posts"
        }

        object Functions {
            const val getCategories = "get_categories"
            const val getDetailedPosts = "get_detailed_posts"
            const val searchPosts = "search_posts"
        }

        object Parameters {
            const val languageCode = "language_code"
            const val categoryIds = "category_ids"
        }

        object Columns {
            const val id = "id"
            const val name = "name"
            const val imageUrl = "image_url"
            const val categoryId = "category_id"
        }

        object Buckets {
            const val postImages = "post-images"
            const val userImages = "user-images" //todo: create the bucket
        }
    }

}