package com.example.domain.authentication

import com.example.data.model.request.VerifyCodeRequest
import com.example.data.repository.AuthRepository
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        otp: String,
    ) {
        val verifyCodeRequest = VerifyCodeRequest(
            mobile = authRepository.getMobile(),
            code = otp,
            fcmDevice = "android",
            fcmToken = "0"
        )
        authRepository.verifyCode(verifyCodeRequest)
    }
}
