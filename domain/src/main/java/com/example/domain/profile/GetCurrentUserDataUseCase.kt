package com.example.domain.profile

import com.example.data.repository.AuthRepository
import com.example.data.repository.UserRepository
import com.example.domain.model.User
import com.example.domain.model.toUser
import javax.inject.Inject

class GetCurrentUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    )  {
    suspend operator fun invoke(userId: String): User{
        val response = userRepository.getCurrentUserById(userId)
        return response.toUser()
    }

    suspend fun getCurrentUserId(): String = authRepository.getStoredAuthData().uuid ?: ""
}