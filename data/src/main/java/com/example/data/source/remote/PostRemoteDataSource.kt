package com.example.data.source.remote

import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.PostItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostRemoteDataSource {


//    @GET("products")
//    suspend fun getProducts(@Query("limit") amount: Int? = null): Response<List<ProductResponse>>

    @GET("post/{post_id}")
    suspend fun getPost(@Path("post_id") postId: String): Response<ApiResponseDto<PostItemDto>>

    @POST("post/store")
    suspend fun addPost(@Body body: PostItemDto): Response<Boolean>

    @POST("post/update")
    suspend fun updatePost(@Body body: PostItemDto): Response<Boolean>

    @POST("post/{post_id}/delete")
    suspend fun deletePost(@Path("post_id") postId: String): Response<Boolean>

}