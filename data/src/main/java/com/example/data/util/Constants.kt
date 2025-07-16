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
        }

        object Parameters {
            const val languageCode = "language_code"
        }

        object Columns {
            const val id = "id"
            const val name = "name"
            const val imageUrl = "image_url"
            const val categoryId = "category_id"
        }

        object Buckets {
            const val postImages = "post-images"
        }
    }

}