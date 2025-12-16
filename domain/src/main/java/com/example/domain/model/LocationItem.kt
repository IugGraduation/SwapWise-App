package com.example.domain.model

import com.example.data.model.response.LocationItemDto

data class LocationItem(
    val id: String = "",
    val name: String = ""
)

fun LocationItemDto.toLocationItem(): LocationItem {
    return LocationItem(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}
