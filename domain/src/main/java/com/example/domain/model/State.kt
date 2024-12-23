package com.example.domain.model

sealed class State<out T> {
    data class Success<T>(val data: T) : State<T>()
    data class Error(val message: String) : State<Nothing>()
    data object Loading : State<Nothing>()

    fun toData(): T? = if(this is Success) data else null
}