package com.example.domain

import com.example.domain.models.User

class GetUserUseCase{
    operator fun invoke(): User {
        return User(
            name = "Cameron Williamson",
            imgResId = R.drawable.img_user_fake
        )
    }
}
