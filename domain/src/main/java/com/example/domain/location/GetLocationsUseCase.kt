package com.example.domain.location

import com.example.data.repository.LocationRepository
import com.example.data.repository.UserRepository
import com.example.domain.model.LocationItem
import com.example.domain.model.toLocationItem
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<LocationItem> {
        val languageCode = userRepository.getLatestSelectedAppLanguage().first()
        return locationRepository.getLocations(languageCode).map { it.toLocationItem() }
    }
}
