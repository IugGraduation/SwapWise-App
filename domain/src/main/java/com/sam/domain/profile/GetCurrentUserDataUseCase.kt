package com.sam.domain.profile

import com.sam.data.repository.AuthRepository
import com.sam.data.repository.UserRepository
import com.sam.domain.model.User
import com.sam.domain.model.toUser
import javax.inject.Inject

class GetCurrentUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    )  {
    suspend operator fun invoke(): User {
        val response = userRepository.getCurrentUserById(getCurrentUserId())
        return response.toUser()
    }

    private suspend fun getCurrentUserId(): String = authRepository.getStoredAuthData().uuid ?: ""
}