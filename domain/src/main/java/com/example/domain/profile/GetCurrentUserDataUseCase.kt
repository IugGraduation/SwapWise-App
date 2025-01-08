package com.example.domain.profile

import com.example.data.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(){

    }
}