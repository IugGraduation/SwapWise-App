package com.example.domain.post

import com.example.data.repository.PostRepository
import com.example.domain.model.PostItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject


class EditPostUseCase @Inject constructor(
    private val validatePostUseCase: ValidatePostUseCase,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(imageRequestBody: RequestBody, postItem: PostItem) {
        validatePostUseCase(
            title = postItem.title,
            place = postItem.place,
            details = postItem.details
        )

        val name = postItem.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val place = postItem.place.toRequestBody("text/plain".toMediaTypeOrNull())
        val details = postItem.details.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryUuid =
            postItem.categoryItem.uuid.toRequestBody("text/plain".toMediaTypeOrNull())

        val fcategory: List<MultipartBody.Part> =
            postItem.favoriteCategoryItems.mapIndexed { index, it ->
                MultipartBody.Part.createFormData("fcategory[$index]", it.uuid)
            }


        val postUuid = postItem.uuid.toRequestBody("text/plain".toMediaTypeOrNull())
        val status =
            (if (postItem.isOpen) "1" else "0").toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart2 = MultipartBody.Part.createFormData(
            "images[0]",
            "IMG_${UUID.randomUUID()}.jpg",
            imageRequestBody,
        )

        val images: List<MultipartBody.Part> = listOf(imagePart2)

        postRepository.updatePost(
            images = images,
            name = name,
            place = place,
            details = details,
            categoryUuid = categoryUuid,
            fcategory = fcategory,
            postUuid = postUuid,
            status = status
        )
    }
}
