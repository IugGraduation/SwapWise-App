package com.example.domain.profile

import android.util.Log
import com.example.data.repository.UserRepository
import com.example.domain.model.User
import javax.inject.Inject

class GetCurrentUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(userId: String): User{
//        Log.e("bk", "from use case: ${userRepository.getCurrentUserById(userId)}")
//        return User.fromProfileDto(profileDto = userRepository.getCurrentUserById(userId))
        Log.e("bk", "getCurrentUserById() is about to be called with userId: $userId")

        val response = userRepository.getCurrentUserById(userId)

        Log.e("bk", "from use case: $response")  // Check if response is null

        return User.fromProfileDto(profileDto = response!!)
    }
}