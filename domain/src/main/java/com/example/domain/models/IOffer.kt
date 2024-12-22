package com.example.domain.models

interface IOffer : TopicItem {
    override val imgResId: Int?
    override val imgContentDescription: String
    val user: User
    override val title: String
    val place: String
    val details: String
    val category: String

    val imgResIdError: String?
    val imgContentDescriptionError: String?
    val titleError: String?
    val placeError: String?
    val detailsError: String?
    val categoryError: String?
}

