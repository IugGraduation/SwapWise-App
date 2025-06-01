package com.example.data.source.remote

import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.ApiResponseDto
import com.example.data.model.response.profile.ProfileDto
import com.example.data.model.response.profile.ProfilePostItemDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileRetrofitDataSource {

    @GET("profile/{user_id}")
    suspend fun getCurrentUserDataById(@Path("user_id") id: String)
            : Response<ApiResponseDto<ProfileDto>>

    @GET("profile/posts")
    suspend fun getCurrentUserPosts(): Response<ApiResponseDto<List<ProfilePostItemDto>>>

    @Multipart
    @POST("profile/update")
    suspend fun updateUserInfo(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("place") place: RequestBody
    ): Response<ApiResponseDto<Nothing>>

    @POST("profile/update/password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ApiResponseDto<Any>>
}