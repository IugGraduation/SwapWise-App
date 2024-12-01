package com.example.domain

import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(password: String): Result<Unit> {
        return if (password.length < 6) {
            Result.failure(Exception(resourceProvider.getString(R.string.password_must_be_at_least_6_characters)))
        } else {
            Result.success(Unit)
        }
    }
}
