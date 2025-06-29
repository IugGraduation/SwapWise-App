package com.example.domain.model

import com.example.data.model.response.UserDto
import com.example.data.model.response.profile.ProfileDto

data class User(
    val uuid: String = "",
    val imageLink: String = "",
    val name: String = "",
    val bio: String = "",
    val phone: String = "",
    val place: String = "",
    val offersNumber: Int = 0,
    val postsNumber: Int = 0,
    val imgContentDescription: String = "",
)

fun UserDto?.toUser(): User {
    return User(
        name = this?.name.toString(),
        imageLink = this?.imageUrl.toString(),
    )
}

fun ProfileDto?.toUser(): User {
    return User(
        uuid = this?.uuid.orEmpty(),
        imageLink = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
        bio = this?.bio.orEmpty(),
        phone = this?.phone.orEmpty(),
        place = this?.place.orEmpty(),
        offersNumber = this?.offers ?: 0,
        postsNumber = this?.posts ?: 0,
    )
}