package com.example.domain.models

data class OfferItem(
    override val imgResId: Int? = null,
    override val imgContentDescription: String = "",
    override val user: User = User(),
    override val title: String = "",
    override val details: String = "",
    override val place: String = "",
    override val category: String = "",

    override val imgResIdError: String? = null,
    override val imgContentDescriptionError: String? = null,
    override val titleError: String? = null,
    override val placeError: String? = null,
    override val detailsError: String? = null,
    override val categoryError: String? = null,
) : IOffer {
    fun isSuccess(): Boolean {
        return (imgResIdError.isNullOrEmpty() && imgContentDescriptionError.isNullOrEmpty() &&
                titleError.isNullOrEmpty() && placeError.isNullOrEmpty() &&
                detailsError.isNullOrEmpty() && categoryError.isNullOrEmpty())
    }
}