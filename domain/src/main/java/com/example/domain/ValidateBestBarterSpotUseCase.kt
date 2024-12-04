package com.example.domain

import com.example.domain.resource.ResourceProvider
import javax.inject.Inject

class ValidateBestBarterSpotUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(fullName: String): Result<Unit> {
        return if (fullName.length < 3) {
            Result.failure(Exception(resourceProvider.getString(R.string.best_barter_spot_must_be_at_least_3_characters)))
        } else {
            Result.success(Unit)
        }
    }
}
