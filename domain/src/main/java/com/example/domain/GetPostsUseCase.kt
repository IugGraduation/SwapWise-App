package com.example.domain

import com.example.domain.models.CategoryItem
import com.example.domain.models.OfferItem
import com.example.domain.models.PostItem

class GetPostsUseCase{
    operator fun invoke(): List<PostItem> {
        val offerItem = OfferItem(
            imgResId = R.drawable.img_top_interactive,
            user = GetUserUseCase()(),
            title = "10kg of Sugar Up for 10kg of Rice ...",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful ...",
        )

        val categoryItem = CategoryItem(title = "Category", imgResId = 0)

        val postItem = PostItem(
            imgResId = R.drawable.img_top_interactive,
            user = GetUserUseCase()(),
            title = "10kg of Sugar Up for 10kg of Rice ...",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful ...",
            onClickMakeOffer = { },
            isOpen = true,
            date = "Wed, Nov 20",
            offers = listOf(offerItem, offerItem, offerItem),
            favoriteCategories = listOf(categoryItem, categoryItem, categoryItem),
            rate = 4.8f
        )
        return listOf(postItem, postItem, postItem, postItem, postItem)
    }
}
