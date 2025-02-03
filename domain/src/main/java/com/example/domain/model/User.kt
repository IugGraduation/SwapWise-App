package com.example.domain.model

import com.example.data.model.UserDto

data class User(
    val uuid: String = "",
    val name: String = "",
    val imageLink: String = "",
    val imgContentDescription: String = "",
    val phone: String = "",
){
    companion object{
        fun fromUserDto(userDto: UserDto?): User{
            return User(name = userDto?.name.toString(), imageLink = userDto?.image.toString())
        }
    }
}