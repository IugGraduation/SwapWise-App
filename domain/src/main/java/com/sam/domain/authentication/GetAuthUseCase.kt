package com.sam.domain.authentication

import com.sam.data.repository.AuthRepository
import com.sam.domain.model.Auth
import com.sam.domain.model.toAuth
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Auth {
        return authRepository.getStoredAuthData().toAuth()
    }

}
