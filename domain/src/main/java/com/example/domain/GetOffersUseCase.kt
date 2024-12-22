package com.example.domain

import com.example.domain.models.OfferItem

class GetOffersUseCase{
    operator fun invoke(): List<OfferItem> {
        val offerItem = OfferItem(
            imgResId = R.drawable.img_top_interactive,
            user = GetUserUseCase()(),
            title = "10kg of Sugar Up for 10kg of Rice",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful",
            category = "Category",
        )

        return listOf(offerItem, offerItem, offerItem)
    }
}
