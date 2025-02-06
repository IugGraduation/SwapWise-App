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
class InvalidTitleException: ValidationException()
class InvalidPlaceException: ValidationException()
class InvalidPhoneException: ValidationException()
class InvalidPasswordException: ValidationException()
class InvalidConfirmPasswordException: ValidationException()
class InvalidBestBarterSpotErrorException: ValidationException()
class InvalidDetailsException: ValidationException()
class InvalidFullNameException: ValidationException()
class InvalidBestBarterSpotException: ValidationException()
