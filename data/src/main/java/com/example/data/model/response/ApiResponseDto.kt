package com.example.data.model.response

data class ApiResponseDto<T>(
    val status: Boolean? = null,
    val message: String? = null,
    val data: T? = null,
)