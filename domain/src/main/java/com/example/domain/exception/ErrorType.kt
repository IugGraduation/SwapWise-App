package com.example.domain.exception

open class SwapWiseException: Exception()

open class EmptyDataException: SwapWiseException()

open class NetworkException: SwapWiseException()
class NoConnectionException: NetworkException()
class ServerException: NetworkException()

open class ValidationException : SwapWiseException()
class UnAuthorizedException: ValidationException()
class MissingRequiredFieldsException: ValidationException()
class PasswordMismatchException: ValidationException()
class InvalidUsernameException: ValidationException()
class InvalidPhoneNumberException: ValidationException()
class InvalidLocationException: ValidationException()
class InvalidEmailException: ValidationException()
