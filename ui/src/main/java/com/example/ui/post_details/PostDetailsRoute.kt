package com.example.ui.post_details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "post_details"

fun NavController.navigateToPostDetails(uuid: String) {
    navigate("$ROUTE/$uuid")
}

fun NavGraphBuilder.postDetailsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${PostDetailsArgs.UUID_ARG}}",
        arguments = listOf(
            navArgument(PostDetailsArgs.UUID_ARG) { NavType.StringType },
        )
    ) { PostDetailsScreen(navController) }
}


class PostDetailsArgs(savedStateHandle: SavedStateHandle) {
    val uuid: String = checkNotNull(savedStateHandle[UUID_ARG])

    companion object {
        const val UUID_ARG = "uuid"
    }
}