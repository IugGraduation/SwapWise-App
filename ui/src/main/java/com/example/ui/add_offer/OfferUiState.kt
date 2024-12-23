package com.example.ui.add_offer

import android.net.Uri
import com.example.domain.model.IOffer
import com.example.domain.model.OfferItem
import com.example.domain.model.User

data class OfferUiState(
    override val uuid: String = "",
    override val image: String = "",
    override val imgContentDescription: String = "",
    override val user: User = User(),
    override val title: String = "",
    override val place: String = "",
    override val details: String = "",
    override val category: String = "",
    val selectedImageUri: Uri? = null,

    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer{

    fun toOfferItem() =
        OfferItem(
            image = image,
            imgContentDescription = imgContentDescription,
            user = user,
            title = title,
            place = place,
            details = details,
            category = category,
        )

    companion object{
        fun fromIOffer(offer: IOffer): OfferUiState{
            return OfferUiState(
                user = offer.user,
                title = offer.title,
                details = offer.details,
                category = offer.category
            )
        }
    }
}
