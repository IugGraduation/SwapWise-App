package com.example.data.model

sealed class StateDto<out T> {
    data class Success<T>(val data: T) : StateDto<T>()
    data class Error(val message: String) : StateDto<Nothing>()
    data object Loading : StateDto<Nothing>()

    fun toData(): T? = if(this is Success) data else null
}