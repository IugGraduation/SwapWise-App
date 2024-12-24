package com.example.domain.model

interface IOffer : TopicItem {
    val user: User
    val place: String
    val details: String
    val category: String
    val date:String

    val imgResIdError: String?
    val imgContentDescriptionError: String?
    val titleError: String?
    val placeError: String?
    val detailsError: String?
    val categoryError: String?
}

