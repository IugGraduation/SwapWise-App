package com.example.domain.authentication

import com.example.data.exception.DataException
import com.example.data.repository.AuthRepository
import com.example.domain.exception.EmptyDataException
import com.example.domain.exception.InactiveAccountException
import com.example.domain.model.Auth
import javax.inject.Inject

class GetAuthDataUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Auth {
        return try {
            Auth.fromAuthDto(authRepository.getAuthDto())
        } catch (e: DataException.EmptyDataException) {
            throw EmptyDataException()
        } catch (e: DataException.InactiveAccountException) {
            throw InactiveAccountException()
        }
    }

}
