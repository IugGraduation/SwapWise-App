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
) {
    companion object {
        fun fromUserDto(userDto: UserDto?): User {
            return User(
                name = userDto?.name.toString(),
                imageLink = userDto?.image.toString(),
            )
        }

        fun fromProfileDto(profileDto: ProfileDto?): User {
            val item = profileDto?.data?.item

                return User(
                    uuid = item?.uuid.orEmpty(),
                    imageLink = item?.image.orEmpty(),
                    name = item?.name.orEmpty(),
                    bio = item?.bio.orEmpty(),
                    phone = item?.mobile.orEmpty(),
                    place = item?.place.orEmpty(),
                    offersNumber = item?.offers ?: 0,
                    postsNumber = item?.posts ?: 0,
                )
            }

    }
}