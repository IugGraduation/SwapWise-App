package com.example.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.util.Screen


fun NavController.navigateToSearch(filterCategoryId: String = "") {
    navigate("${Screen.Search.route}/$filterCategoryId") {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.searchRoute(navController: NavHostController){
    composable(
        route = "${Screen.Search.route}/{${SearchArgs.FILTER_CATEGORY_ID_ARG}}",
        arguments = listOf(
            navArgument(SearchArgs.FILTER_CATEGORY_ID_ARG) { NavType.StringType },
        )
    ) { SearchScreen(navController) }
}

class SearchArgs(savedStateHandle: SavedStateHandle) {
    val filterCategoryId: String = checkNotNull(savedStateHandle[FILTER_CATEGORY_ID_ARG])

    companion object {
        const val FILTER_CATEGORY_ID_ARG = "filter_category_id"
    }
}