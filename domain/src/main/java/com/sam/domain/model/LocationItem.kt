package com.sam.domain.model

import com.sam.data.model.response.LocationItemDto

data class LocationItem(
    val id: String = "",
    val name: String = ""
) {
    companion object {
        fun fromLocationItemDto(locationItemDto: LocationItemDto): LocationItem {
            return LocationItem(
                id = locationItemDto.id.orEmpty(),
                name = locationItemDto.name.orEmpty(),
            )
        }
    }
}

fun LocationItemDto.toLocationItem(): LocationItem {
    return LocationItem(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}
