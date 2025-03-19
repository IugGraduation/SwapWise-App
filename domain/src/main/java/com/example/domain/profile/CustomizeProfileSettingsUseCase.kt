package com.example.domain.profile

import com.example.data.repository.UserRepository
import javax.inject.Inject

class CustomizeProfileSettingsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun updateDarkTheme(isDarkTheme: Boolean) = repository.updateDarkTheme(isDarkTheme)

    suspend fun isDarkThem() = repository.isDarkThemeEnabled()

    suspend fun updateAppLanguage(newLanguage: String) = repository.updateAppLanguage(newLanguage)

    fun getLatestSelectedAppLanguage() = repository.getLatestSelectedAppLanguage()
}