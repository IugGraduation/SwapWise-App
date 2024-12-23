package com.example.domain

import com.example.domain.model.User

class GetUserUseCase{
    operator fun invoke(): User {
        return User(
            name = "Cameron Williamson",
            image = R.drawable.img_user_fake.toString()
        )
    }
}
