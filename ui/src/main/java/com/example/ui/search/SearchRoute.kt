package com.example.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "search"

fun NavController.navigateToSearch(
    filterCategoryName: String = "",
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$ROUTE/$filterCategoryName", builder)
}

fun NavGraphBuilder.searchRoute(navController: NavHostController){
    composable(
        route = "$ROUTE/{${SearchArgs.FILTER_CATEGORY_NAME_ARG}}",
        arguments = listOf(
            navArgument(SearchArgs.FILTER_CATEGORY_NAME_ARG) { NavType.StringType },
        )
    ) { SearchScreen(navController) }
}


class SearchArgs(savedStateHandle: SavedStateHandle) {
    val filterCategoryName: String = checkNotNull(savedStateHandle[FILTER_CATEGORY_NAME_ARG])

    companion object {
        const val FILTER_CATEGORY_NAME_ARG = "filter_category_name"
    }
}