package com.sam.domain.location

import com.sam.data.model.response.LocationItemDto
import com.sam.data.repository.LocationRepository
import com.sam.data.repository.UserRepository
import com.sam.domain.model.LocationItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLocationsUseCaseTest {

    // 1. Dependencies (Mocks)
    private val locationRepository: LocationRepository = mockk()
    private val userRepository: UserRepository = mockk()

    // 2. System Under Test (SUT)
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    fun setUp() {
        getLocationsUseCase = GetLocationsUseCase(locationRepository, userRepository)
    }

    @Test
    fun `invoke should fetch language code, get DTOs from repo, and return mapped items`() = runTest {
        // Given
        val expectedLanguage = "en"
        val locationDtos = listOf(
            LocationItemDto(id = "1", name = "Gaza"),
            LocationItemDto(id = "2", name = "London")
        )
        val expectedDomainItems = listOf(
            LocationItem(id = "1", name = "Gaza"),
            LocationItem(id = "2", name = "London")
        )

        coEvery { userRepository.getLatestSelectedAppLanguage() } returns flowOf(expectedLanguage)
        coEvery { locationRepository.getLocations(expectedLanguage) } returns locationDtos

        // When
        val result = getLocationsUseCase()

        // Then
        assertEquals(expectedDomainItems, result)
        coVerify(exactly = 1) { userRepository.getLatestSelectedAppLanguage() }
        coVerify(exactly = 1) { locationRepository.getLocations(expectedLanguage) }
    }

    @Test
    fun `invoke should return empty list when repository returns empty DTO list`() = runTest {
        // Given
        val expectedLanguage = "ar"
        coEvery { userRepository.getLatestSelectedAppLanguage() } returns flowOf(expectedLanguage)
        coEvery { locationRepository.getLocations(expectedLanguage) } returns emptyList()

        // When
        val result = getLocationsUseCase()

        // Then
        assertEquals(0, result.size)
    }

    @Test(expected = Exception::class)
    fun `invoke should throw exception when repository fails`() = runTest {
        // Given
        coEvery { userRepository.getLatestSelectedAppLanguage() } returns flowOf("en")
        coEvery { locationRepository.getLocations(any()) } throws Exception("Database error")

        // When
        getLocationsUseCase()

        // Then (Exception expected)
    }
}
