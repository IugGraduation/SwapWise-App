package com.sam.domain.authentication

import com.sam.data.repository.AuthRepository
import com.sam.domain.exception.EmptyDataException
import com.sam.domain.exception.InactiveAccountException
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        if (authRepository.checkIsAuthDtoStored().not()) {
            if (authRepository.checkIsAccountActive().not() && authRepository.getPhone()
                    .isNotBlank()
            ) throw InactiveAccountException()
            throw EmptyDataException()
        }
    }

}
