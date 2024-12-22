package com.example.domain

import javax.inject.Inject

class GetCategoriesNamesUseCase @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase
){
    operator fun invoke(): List<String> {

        return getCategoriesUseCase().map { it.title }
    }
}
