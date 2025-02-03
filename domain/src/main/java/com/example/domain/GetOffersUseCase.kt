package com.example.domain

import com.example.domain.model.OfferItem
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(){
    operator fun invoke(): List<OfferItem> {
       return GetFakeOffersUseCase()()
    }
}


class GetFakeOffersUseCase{
    operator fun invoke(): List<OfferItem> {
        val offerItem = OfferItem(
            imageLink = R.drawable.img_top_interactive.toString(),
            user = GetUserUseCase()(),
            title = "10kg of Sugar Up for 10kg of Rice",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful",
            category = "Category",
            date = "11/11/11"
        )

        return listOf(offerItem, offerItem, offerItem)
    }
}
