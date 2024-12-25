package com.example.domain

import javax.inject.Inject

class GetCategoriesNamesUseCase @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase
) {
    suspend operator fun invoke(): List<String> {
        return getCategoriesUseCase().map { it.title }
    }


    fun getFakeData(): List<String> {
        return getCategoriesUseCase.getFakeData().map { it.title }
    }
}
