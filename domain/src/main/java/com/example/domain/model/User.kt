package com.example.domain.model

import com.example.data.model.response.UserDto
import com.example.data.model.response.profile.ProfileDto

data class User(
    val id: String = "",
    val imageLink: String = "",
    val name: String = "",
    val phone: String = "",
    val locationId: String = "",
    val bio: String = "",
    val offersNumber: Int = 0,
    val postsNumber: Int = 0,
)

fun UserDto?.toUser(): User {
    return User(
        id = this?.id.orEmpty(),
        imageLink = this?.imageUrl.orEmpty(),
        name = this?.name.orEmpty(),
        phone = this?.phone.orEmpty(),
    )
}

fun ProfileDto?.toUser(): User {
    return User(
        id = this?.id.orEmpty(),
        imageLink = this?.imageUrl.orEmpty(),
        name = this?.name.orEmpty(),
        phone = this?.phone.orEmpty(),
        locationId = this?.locationId.orEmpty(),
        bio = this?.bio.orEmpty(),
        offersNumber = this?.offers ?: 0,
        postsNumber = this?.posts ?: 0,
    )
}