package com.example.domain.profile

import android.util.Log
import com.example.data.repository.AuthRepository
import com.example.data.repository.UserRepository
import com.example.domain.model.User
import javax.inject.Inject

class GetCurrentUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    )  {
    suspend operator fun invoke(userId: String): User{
        val response = userRepository.getCurrentUserById(userId)
        return User.fromData(profileData = response)
    }

    suspend fun getCurrentUserId(): String = authRepository.getAuthDto().uuid ?: ""
}