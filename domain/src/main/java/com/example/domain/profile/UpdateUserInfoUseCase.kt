package com.example.domain.profile

import android.util.Log
import com.example.data.repository.UserRepository
import com.example.domain.exception.FailedToUpdateUserInfoException
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        phoneNumber: String,
        location: String,
        imageRequestBody: RequestBody?,
        bio: String
    ): Boolean {
        if (!validateUserInfo(name, phoneNumber, location)) throw FailedToUpdateUserInfoException()

        Log.e(
            "bk",
            "Updating user info: ($name, $phoneNumber, $location, $imageRequestBody is valid)"
        )

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        val locationBody = location.toRequestBody("text/plain".toMediaTypeOrNull())
        val bioBody = bio.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = imageRequestBody?.let {
            MultipartBody.Part.createFormData(
                name = "image", filename = "IMG_${UUID.randomUUID()}.jpg", body = it
            )
        }
        Log.e(
            "bk",
            "UpdateUserInfoUseCase: imagePart (${imagePart?.headers?.names()})"
        )
        return userRepository.updateUserInfo(
            name = nameBody,
            mobile = phoneBody,
            place = locationBody,
            image = imagePart,
            bio = bioBody
        )
    }



//    suspend operator fun invoke(
//        name: String,
//        phoneNumber: String,
//        location: String,
//        image: String,
//        bio: String
//    ): Boolean {
//        if (validateUserInfo(name = name, phoneNumber = phoneNumber, location = location))
//            return userRepository.updateUerInfo(
//                name = name,
//                mobile = phoneNumber,
//                place = location,
//                image = image,
//                bio = bio
//            )
//        else
//            throw FailedToUpdateUserInfoException()
//    }

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