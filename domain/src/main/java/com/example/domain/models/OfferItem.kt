package com.example.domain.models

open class OfferItem(
    override val imgResId: Int,
    override val imgContentDescription: String = "",
    open val user: User,
    override val title: String,
    open val details: String,
) : TopicItem

