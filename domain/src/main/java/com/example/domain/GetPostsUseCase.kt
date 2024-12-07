package com.example.domain

import com.example.domain.models.PostItem

class GetPostsUseCase{
    operator fun invoke(): List<PostItem> {
        val postItem = PostItem(
            imgResId = R.drawable.img_top_interactive,
            user = GetUserUseCase().invoke(),
            title = "10kg of Sugar Up for 10kg of Rice ...",
            details = "Looking for a sweet deal? I have 10 kilograms of high-quality sugar that I’d like to exchange for something useful ...",
            onClickMakeOffer = { },
            isOpen = true,
            offersCount = 4
        )
        return listOf(postItem, postItem, postItem, postItem, postItem)
    }
}
