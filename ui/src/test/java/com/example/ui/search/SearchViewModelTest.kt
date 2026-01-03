package com.example.ui.search

import com.example.domain.category.GetCategoriesUseCase
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.CategoryItem
import com.example.domain.model.LocationItem
import com.example.domain.model.PostItem
import com.example.domain.search.GetSearchResultUseCase
import com.example.ui.MainDispatcherRule
import com.example.ui.base.StringsResource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks
    private val stringsResource = mockk<StringsResource>(relaxed = true)
    private val getSearchResultUseCase = mockk<GetSearchResultUseCase>()
    private val getCategoriesUseCase = mockk<GetCategoriesUseCase>()
    private val getLocationsUseCase = mockk<GetLocationsUseCase>()

    private lateinit var viewModel: SearchViewModel

    private val mockCategories = listOf(CategoryItem(id = "c1", name = "Food"))
    private val mockLocations = listOf(LocationItem(id = "l1", name = "Gaza"))

    @Before
    fun setUp() {
        coEvery { getCategoriesUseCase() } returns mockCategories
        coEvery { getLocationsUseCase() } returns mockLocations
    }

    @Test
    fun `init should load filters and update state`() = runTest {
        // When
        viewModel = SearchViewModel(
            stringsResource,
            getSearchResultUseCase,
            getCategoriesUseCase,
            getLocationsUseCase
        )
        advanceUntilIdle()

        // Then
        assertEquals(1, viewModel.state.value.data.categoriesFilter.items.size)
        assertEquals(1, viewModel.state.value.data.locationsFilter.items.size)
        assertEquals("Food", viewModel.state.value.data.categoriesFilter.items[0].categoryItem.name)
        assertEquals("Gaza", viewModel.state.value.data.locationsFilter.items[0].categoryItem.name)
    }

    @Test
    fun `onSearchChange should update search text in state`() = runTest {
        // Given
        viewModel = SearchViewModel(
            stringsResource,
            getSearchResultUseCase,
            getCategoriesUseCase,
            getLocationsUseCase
        )
        val query = "Olive Oil"

        // When
        viewModel.onSearchChange(query)

        // Then
        assertEquals(query, viewModel.state.value.data.search)
    }

    @Test
    fun `search with chips should call GetSearchResultUseCase with correct IDs`() = runTest {
        // Given
        val mockResults = listOf(PostItem(id = "p1", name = "Post 1"))
        coEvery { getSearchResultUseCase(any(), any(), any()) } returns mockResults
        
        viewModel = SearchViewModel(
            stringsResource,
            getSearchResultUseCase,
            getCategoriesUseCase,
            getLocationsUseCase
        )
        advanceUntilIdle()

        // Manually select a category chip
        val categoryChip = viewModel.state.value.data.categoriesFilter.items[0]
        categoryChip.selected.value = true

        // When
        // we trigger the search interaction to verify logic
        viewModel.onClickTryAgain()
        advanceUntilIdle()

        // Then
        coVerify { 
            getSearchResultUseCase(
                searchValue = "",
                categoryIdsFilter = listOf("c1"),
                locationIdsFilter = emptyList()
            )
        }
        assertEquals(mockResults, viewModel.state.value.data.topicsList)
        assertEquals(false, viewModel.state.value.data.emptyResult)
    }

    @Test
    fun `failed search should show empty state and hide results`() = runTest {
        // Given
        coEvery { getSearchResultUseCase(any(), any(), any()) } throws Exception("Network Error")
        
        viewModel = SearchViewModel(
            stringsResource,
            getSearchResultUseCase,
            getCategoriesUseCase,
            getLocationsUseCase
        )
        viewModel.onSearchChange("test")
        
        // When
        viewModel.onClickTryAgain()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.data.topicsList.isEmpty())
        assertTrue(viewModel.state.value.data.emptyResult)
    }
}
