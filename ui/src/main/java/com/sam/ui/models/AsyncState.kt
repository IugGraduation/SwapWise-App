package com.sam.ui.models

sealed class AsyncState<out T> {
    open val data: T? = null

    object Initial : AsyncState<Nothing>()
    object Loading : AsyncState<Nothing>()
    data class Success<out T>(override val data: T) : AsyncState<T>()
    data class Error(val message: String) : AsyncState<Nothing>()

    /**
     * Maps the data within a [Success] state to a new type, while preserving
     * other states like [Loading], [Error], and [Initial].
     *
     * @param transform The function to apply to the data if the state is [Success].
     * @return A new [AsyncState] with the transformed data, or the original state if not [Success].
     */
    inline fun <R> mapData(crossinline transform: (T) -> R): AsyncState<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Initial -> this
            is Loading -> this
        }
    }
}