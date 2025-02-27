package com.example.domain.post

import com.example.domain.exception.InvalidDetailsException
import com.example.domain.exception.InvalidPlaceException
import com.example.domain.exception.InvalidTitleException
import javax.inject.Inject

class ValidatePostUseCase @Inject constructor() {
    operator fun invoke(
        title: String,
        place: String,
        details: String,
    ) {
        validateTitle(title)
        validatePlace(place)
        validateDetails(details)
    }
}

private fun validateTitle(input: String) {
    if (input.length < 3) throw InvalidTitleException()
}

private fun validatePlace(input: String) {
    if (input.length < 3) throw InvalidPlaceException()
}

private fun validateDetails(input: String) {
    if (input.length < 3) throw InvalidDetailsException()
}