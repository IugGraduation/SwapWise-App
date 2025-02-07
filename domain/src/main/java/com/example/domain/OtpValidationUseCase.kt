package com.example.domain

import kotlinx.coroutines.delay
import javax.inject.Inject

class OtpValidationUseCase @Inject constructor() {
    suspend operator fun invoke(
        otp: String,
    ) {
        delay(500)
        //todo: confirm otp from server
    }
}
