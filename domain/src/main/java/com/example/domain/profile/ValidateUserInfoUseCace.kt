package com.example.domain.profile

import com.example.data.repository.UserRepository
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import javax.inject.Inject

class ValidateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {

    suspend operator fun invoke(
        name: String,
        phoneNumber: String,
        location: String,
    ){
        validateUsername(name = name)
        validatePhoneNumber(number = phoneNumber)
        validateLocation(location = location)
        //todo: go to edit using api
    }

    private fun validateUsername(name: String){
        val validUsernamePattern = Regex("^[a-zA-Z][a-zA-Z0-9_ ]{2,}$")

        if(!validUsernamePattern.matches(name) || name.isBlank()){
            throw InvalidUsernameException()
        }
    }

    private fun validatePhoneNumber(number:String){
        if(number.isBlank()) throw InvalidPhoneNumberException()
    }

    private fun validateLocation(location: String){
        if(location.isBlank()) throw InvalidLocationException()
    }
}