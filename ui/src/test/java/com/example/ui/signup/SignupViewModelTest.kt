package com.example.ui.signup

import com.example.domain.authentication.SignupUseCase
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.LocationItem
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.ui.MainDispatcherRule
import com.example.ui.base.StringsResource
import com.example.ui.shared.BottomNavigationViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignupViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks
    private val stringsResource = mockk<StringsResource>(relaxed = true)
    private val customizeProfileSettings = mockk<CustomizeProfileSettingsUseCase>(relaxed = true)
    private val getLocationsUseCase = mockk<GetLocationsUseCase>()
    private val signupUseCase = mockk<SignupUseCase>()
    private val bottomNavigationViewModel = mockk<BottomNavigationViewModel>(relaxed = true)

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        // We set up standard behavior for the theme flow
        coEvery { customizeProfileSettings.isDarkThem() } returns flowOf(false)
    }

    @Test
    fun `init should fetch locations and update state`() = runTest {
        // Given
        val expectedLocations = listOf(LocationItem(id = "1", name = "Gaza"))
        coEvery { getLocationsUseCase() } returns expectedLocations

        // When
        viewModel = SignupViewModel(
            stringsResource,
            customizeProfileSettings,
            getLocationsUseCase,
            signupUseCase,
            bottomNavigationViewModel
        )
        advanceUntilIdle() // Wait for init blocks to finish

        // Then
        val actualLocations = viewModel.state.value.data.locationDropdown.items
        assertEquals(expectedLocations, actualLocations)
        assertEquals(false, viewModel.state.value.data.locationDropdown.isLoading)
    }

    @Test
    fun `onFullNameChange should update fullName in state`() = runTest {
        // Given
        coEvery { getLocationsUseCase() } returns emptyList()
        viewModel = SignupViewModel(
            stringsResource,
            customizeProfileSettings,
            getLocationsUseCase,
            signupUseCase,
            bottomNavigationViewModel
        )
        val newName = "John Doe"

        // When
        viewModel.onFullNameChange(newName)

        // Then
        assertEquals(newName, viewModel.state.value.data.fullName)
    }

    @Test
    fun `getLocations failure should set error in dropdown state`() = runTest {
        // Given
        val errorMessage = "Failed to fetch"
        coEvery { getLocationsUseCase() } throws Exception(errorMessage)

        // When
        viewModel = SignupViewModel(
            stringsResource,
            customizeProfileSettings,
            getLocationsUseCase,
            signupUseCase,
            bottomNavigationViewModel
        )
        advanceUntilIdle()

        // Then
        assertEquals(errorMessage, viewModel.state.value.data.locationDropdown.error)
        assertEquals(false, viewModel.state.value.data.locationDropdown.isLoading)
    }
}
