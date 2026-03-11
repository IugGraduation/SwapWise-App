package com.sam.domain.authentication

import com.sam.data.model.request.VerifyCodeRequest
import com.sam.data.repository.AuthRepository
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        otp: String,
    ) {
        val verifyCodeRequest = VerifyCodeRequest(
            phone = authRepository.getPhone(),
            code = otp,
            fcmDevice = "android",
            fcmToken = "0"
        )
        authRepository.verifyCode(verifyCodeRequest)
    }
}
