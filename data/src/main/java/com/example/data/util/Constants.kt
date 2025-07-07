package com.example.data.util

object Constants {
    const val TOKEN = "token"

    object Supabase {
        object Tables {
            const val categories = "categories"
            const val users = "users"
            const val posts = "posts"
            const val detailedPosts = "detailed_posts"
        }

        object Columns {
            const val id = "id"
            const val name = "name"
            const val imageUrl = "imageUrl" //todo: check if imageUrl or image_url
        }

        object Buckets {
            const val postImages = "post-images"
        }
    }

}