package com.example.domain.post

import com.example.domain.exception.InvalidCategoryException
import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import javax.inject.Inject

class ValidatePostUseCase @Inject constructor() {
    operator fun invoke(
        title: String,
        locationId: String,
        details: String,
        categoryId: String
    ) {
        validateTitle(title)
        validateLocation(locationId)
        validateDetails(details)
        validateCategory(categoryId)
    }
}

private fun validateTitle(input: String) {
    if (input.length < 3) throw InvalidTitleException()
}

private fun validateLocation(input: String) {
    if (input.isNotBlank()) throw InvalidPlaceException()
}

private fun validateDetails(input: String) {
    if (input.length < 3) throw InvalidDetailsException()
}

private fun validateCategory(input: String) {
    if (input.isEmpty()) throw InvalidCategoryException()
}