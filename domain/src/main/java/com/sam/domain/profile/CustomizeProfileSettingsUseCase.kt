package com.sam.domain.profile

import com.sam.data.repository.HomeRepository
import com.sam.data.repository.UserRepository
import javax.inject.Inject

class CustomizeProfileSettingsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) {
    suspend fun updateDarkTheme(isDarkTheme: Boolean) = userRepository.updateDarkTheme(isDarkTheme)

    suspend fun isDarkThem() = userRepository.isDarkThemeEnabled()

    suspend fun updateAppLanguage(newLanguage: String) {
        userRepository.updateAppLanguage(newLanguage)
        homeRepository.clearCategoriesCache()
    }

    fun getLatestSelectedAppLanguage() = userRepository.getLatestSelectedAppLanguage()
}