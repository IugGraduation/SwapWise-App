package com.example.domain.location

import com.example.data.model.response.LocationItemDto
import com.example.data.repository.LocationRepository
import com.example.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLocationsUseCaseTest {

    // Dependencies (Mocks)
    private val locationRepository: LocationRepository = mockk()
    private val userRepository: UserRepository = mockk()

    // System Under Test
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    fun setUp() {
        getLocationsUseCase = GetLocationsUseCase(locationRepository, userRepository)
    }

    @Test
    fun `invoke should fetch language code and return locations from repository`() = runTest {
        // Given
        val expectedLanguage = "en"
        val expectedLocations = listOf(
            LocationItemDto(id = "1", name = "Gaza"),
            LocationItemDto(id = "2", name = "London")
        )

        coEvery { userRepository.getLatestSelectedAppLanguage() } returns flowOf(expectedLanguage)
        coEvery { locationRepository.getLocations(expectedLanguage) } returns expectedLocations

        // When
        val result = getLocationsUseCase()

        // Then
        assertEquals(expectedLocations, result)
        coVerify(exactly = 1) { userRepository.getLatestSelectedAppLanguage().first() }
        coVerify(exactly = 1) { locationRepository.getLocations(expectedLanguage) }
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
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
