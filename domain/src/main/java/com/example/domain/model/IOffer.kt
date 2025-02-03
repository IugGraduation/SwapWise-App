package com.example.domain.model

interface IOffer : TopicItem {
    val user: User
    val place: String
    val details: String
    val category: String
    val date: String

    val imgResIdError: String?
    val imgContentDescriptionError: String?
    val titleError: String?
    val placeError: String?
    val detailsError: String?
    val categoryError: String?


    fun isSuccess(): Boolean {
        return (imgResIdError.isNullOrEmpty() && imgContentDescriptionError.isNullOrEmpty() &&
                titleError.isNullOrEmpty() && placeError.isNullOrEmpty() &&
                detailsError.isNullOrEmpty() && categoryError.isNullOrEmpty())
    }


    fun customCopy(
        uuid: String = this.uuid,
        title: String = this.title,
        image: String = this.imageLink,
        imgContentDescription: String = this.imgContentDescription,
        user: User = this.user,
        place: String = this.place,
        details: String = this.details,
        category: String = this.category,
        date: String = this.date,

        imgResIdError: String? = this.imgResIdError,
        imgContentDescriptionError: String? = this.imgContentDescriptionError,
        titleError: String? = this.titleError,
        placeError: String? = this.placeError,
        detailsError: String? = this.detailsError,
        categoryError: String? = this.categoryError,
    ): IOffer {
        return when (this) {
            is OfferItem -> {
                this.copy(
                    user = user,
                    place = place,
                    details = details,
                    category = category,
                    date = date,
                    imgResIdError = imgResIdError,
                    imgContentDescriptionError = imgContentDescriptionError,
                    titleError = titleError,
                    placeError = placeError,
                    detailsError = detailsError,
                    categoryError = categoryError,
                    uuid = uuid,
                    title = title,
                    imageLink = image,
                    imgContentDescription = imgContentDescription,
                )
            }

            is PostItem -> {
                this.copy(
                    user = user,
                    place = place,
                    details = details,
                    category = category,
                    date = date,
                    imgResIdError = imgResIdError,
                    imgContentDescriptionError = imgContentDescriptionError,
                    titleError = titleError,
                    placeError = placeError,
                    detailsError = detailsError,
                    categoryError = categoryError,
                    uuid = uuid,
                    title = title,
                    imageLink = image,
                    imgContentDescription = imgContentDescription,
                )
            }

            else -> {
                return object : IOffer {
                    override val user: User = user
                    override val place: String = place
                    override val details: String = details
                    override val category: String = category
                    override val date: String = date
                    override val imgResIdError: String? = imgResIdError
                    override val imgContentDescriptionError: String? = imgContentDescriptionError
                    override val titleError: String? = titleError
                    override val placeError: String? = placeError
                    override val detailsError: String? = detailsError
                    override val categoryError: String? = categoryError
                    override val uuid: String = uuid
                    override val title: String = title
                    override val imageLink: String = image
                    override val imgContentDescription: String = imgContentDescription
                }
            }
        }
    }
}

