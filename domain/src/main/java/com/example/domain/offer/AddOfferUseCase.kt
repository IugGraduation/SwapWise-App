package com.example.domain.offer

import com.example.data.repository.OfferRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.model.OfferItem
import com.example.domain.model.PostItem
import com.example.domain.post.ValidatePostUseCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject


class AddOfferUseCase @Inject constructor(
    private val validateOfferUseCase: ValidatePostUseCase,
    private val offerRepository: OfferRepository
) {
    suspend operator fun invoke(postId: String, offerItem: OfferItem): Boolean {
        validateOfferUseCase(
            title = offerItem.title,
            place = offerItem.place,
            details = offerItem.details
        )
        return offerRepository.addOffer(postId, offerItem.toOfferItemDto())
            ?: throw EmptyDataException()
    }

    suspend operator fun invoke(imageRequestBody: RequestBody, offerItem: PostItem) {
        validateOfferUseCase(
            title = offerItem.title,
            place = offerItem.place,
            details = offerItem.details
        )

        val name = offerItem.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val place = offerItem.place.toRequestBody("text/plain".toMediaTypeOrNull())
        val details = offerItem.details.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryUuid = offerItem.categoryItem.uuid.toRequestBody("text/plain".toMediaTypeOrNull())

        val fcategory: List<MultipartBody.Part> = offerItem.favoriteCategoryItems.mapIndexed { index, it ->
            MultipartBody.Part.createFormData("fcategory[$index]", it.uuid)
        }

        val imagePart2 = MultipartBody.Part.createFormData(
            "images[0]",
            "IMG_${UUID.randomUUID()}.jpg",
            imageRequestBody,
        )

        val images: List<MultipartBody.Part> = listOf(imagePart2)

        offerRepository.addOffer(
            images = images,
            name = name,
            place = place,
            details = details,
            categoryUuid = categoryUuid,
            fcategory = fcategory
        )
    }
}
