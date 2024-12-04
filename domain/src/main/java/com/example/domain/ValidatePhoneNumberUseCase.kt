package com.example.domain

import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(phone: String): Result<Unit> {
        return if (!phone.matches(Regex("^[0-9]{10}$"))) {
            Result.failure(Exception(resourceProvider.getString(R.string.phone_number_must_be_10_digits_long)))
        } else {
            Result.success(Unit)
        }
    }
}
