package com.example.domain.authentication

import com.example.data.repository.AuthRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.exception.InactiveAccountException
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        if (authRepository.checkIsAuthDtoStored().not()) {
            if (authRepository.checkIsAccountActive().not()) throw InactiveAccountException()
            throw EmptyDataException()
        }
    }

}
