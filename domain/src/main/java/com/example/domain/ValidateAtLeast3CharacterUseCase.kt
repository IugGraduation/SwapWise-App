package com.example.domain

import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class ValidateAtLeast3CharacterUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(input: String, inputName: String): Result<Unit> {
        return if (input.length < 3) {
            Result.failure(Exception(inputName + " " + resourceProvider.getString(R.string.must_be_at_least_3_characters)))
        } else {
            Result.success(Unit)
        }
    }
}
