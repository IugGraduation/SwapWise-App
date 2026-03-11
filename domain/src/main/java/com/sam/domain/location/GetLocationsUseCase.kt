package com.sam.domain.location

import com.sam.data.repository.LocationRepository
import com.sam.data.repository.UserRepository
import com.sam.domain.model.LocationItem
import com.sam.domain.model.toLocationItem
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
