package com.example.domain.profile

import com.example.data.repository.UserRepository
import com.example.domain.exception.FailedToUpdateUserInfoException
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        mobile: String,
        location: String,
        imageByteArray: ByteArray?,
        bio: String
    ): Boolean {
        if (!validateUserInfo(name, mobile, location)) throw FailedToUpdateUserInfoException()

        return userRepository.updateUserInfo(
            name = name,
            mobile = mobile,
            place = location,
            imageByteArray = imageByteArray,
            bio = bio
        )
    }

    private fun validateUserInfo(
        name: String,
        phoneNumber: String,
        location: String,
    ): Boolean {
        validateUsername(name = name)
        validatePhoneNumber(number = phoneNumber)
        validateLocation(location = location)

        return true
    }

    private fun validateUsername(name: String) {
        val validUsernamePattern = Regex("^[a-zA-Z][a-zA-Z0-9_ ]{2,}$")

        if (!validUsernamePattern.matches(name) || name.isBlank()) {
            throw InvalidUsernameException()
        }
    }

    private fun validatePhoneNumber(number: String) {
        if (number.isBlank()) throw InvalidPhoneNumberException()
    }

    private fun validateLocation(location: String) {
        if (location.isBlank()) throw InvalidLocationException()
    }
}