package com.example.domain.offer

import com.example.data.repository.OfferRepository
import com.example.domain.model.OfferItem
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
    suspend operator fun invoke(
        imageRequestBody: RequestBody,
        postId: String,
        offerItem: OfferItem
    ) {
        validateOfferUseCase(
            title = offerItem.title,
            place = offerItem.place,
            details = offerItem.details
        )

        val name = offerItem.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val place = offerItem.place.toRequestBody("text/plain".toMediaTypeOrNull())
        val details = offerItem.details.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryUuid = offerItem.categoryItem.uuid.toRequestBody("text/plain".toMediaTypeOrNull())
        val postUuid = postId.toRequestBody("text/plain".toMediaTypeOrNull())

        val image = MultipartBody.Part.createFormData(
            "image",
            "IMG_${UUID.randomUUID()}.jpg",
            imageRequestBody,
        )


        offerRepository.addOffer(
            image = image,
            name = name,
            place = place,
            details = details,
            categoryUuid = categoryUuid,
            postUuid = postUuid,
        )
    }
}
