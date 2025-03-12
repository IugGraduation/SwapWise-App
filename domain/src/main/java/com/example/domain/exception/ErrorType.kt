package com.example.domain.exception

open class SwapWiseException: Exception()

open class EmptyDataException: SwapWiseException()

open class NetworkException: SwapWiseException()
class NoConnectionException: NetworkException()
class ServerException: NetworkException()

open class ValidationException : SwapWiseException()
class UnAuthorizedException: ValidationException()
class MissingRequiredFieldsException: ValidationException()
class InactiveAccountException: ValidationException()
class InvalidUsernameException: ValidationException()
class InvalidPhoneNumberException: ValidationException()
class InvalidLocationException: ValidationException()
class InvalidEmailException: ValidationException()
class InvalidTitleException: ValidationException()
class InvalidPlaceException: ValidationException()
class InvalidPhoneException: ValidationException()

class PasswordMismatchException: ValidationException()
class InvalidPasswordException: ValidationException()
class InvalidNewPasswordException: ValidationException()
class InvalidConfirmPasswordException: ValidationException()
class EmptyPasswordException: ValidationException()
class EmptyNewPasswordException: ValidationException()
class EmptyConfirmPasswordException: ValidationException()
class SamePasswordException: ValidationException()

class InvalidDetailsException: ValidationException()
class InvalidFullNameException: ValidationException()
class InvalidBestBarterSpotException: ValidationException()
class EmptyImageException: ValidationException()

class FailedToUpdateUserInfoException: SwapWiseException()
