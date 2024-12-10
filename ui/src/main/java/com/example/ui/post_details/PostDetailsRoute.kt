package com.example.ui.post_details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "post_details"

fun NavController.navigateToPostDetails(postTitle: String) {
    navigate("$ROUTE/$postTitle")
}

fun NavGraphBuilder.postDetailsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${PostDetailsArgs.POST_TITLE_ARG}}",
        arguments = listOf(
            navArgument(PostDetailsArgs.POST_TITLE_ARG) { NavType.StringType },
        )
    ) { PostDetailsScreen(navController) }
}


class PostDetailsArgs(savedStateHandle: SavedStateHandle) {
    val postTitle: String = checkNotNull(savedStateHandle[POST_TITLE_ARG])

    companion object {
        const val POST_TITLE_ARG = "post_title"
    }
}