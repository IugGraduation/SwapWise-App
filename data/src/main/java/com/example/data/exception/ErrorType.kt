package com.example.data.exception

sealed class DataException : Exception() {

    open class EmptyDataException : DataException()

    open class NetworkException : DataException()
    class NoConnectionException : NetworkException()
    class ServerException : NetworkException()

    open class ValidationException : DataException()
    class UnAuthorizedException : ValidationException()
    class MissingRequiredFieldsException : ValidationException()
    class InactiveAccountException : ValidationException()
}