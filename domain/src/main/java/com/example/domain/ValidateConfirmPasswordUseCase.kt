package com.example.domain

import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(password: String, confirmPassword: String): Result<Unit> {
        return if (password != confirmPassword) {
            Result.failure(Exception(resourceProvider.getString(R.string.passwords_do_not_match)))
        } else {
            Result.success(Unit)
        }
    }
}
