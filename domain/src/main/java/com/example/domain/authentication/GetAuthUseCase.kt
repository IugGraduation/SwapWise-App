package com.example.domain.authentication

import com.example.data.repository.AuthRepository
import com.example.domain.model.Auth
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Auth {
        return Auth.fromAuthDto(authRepository.getAuthDto())
    }

}
