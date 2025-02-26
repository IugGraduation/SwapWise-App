package com.example.ui.post_details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "post_details"

fun NavController.navigateToPostDetails(
    postId: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$ROUTE/$postId", builder)
}

fun NavGraphBuilder.postDetailsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${PostDetailsArgs.POST_ID}}",
        arguments = listOf(
            navArgument(PostDetailsArgs.POST_ID) { NavType.StringType },
        )
    ) { PostDetailsScreen(navController) }
}


class PostDetailsArgs(savedStateHandle: SavedStateHandle) {
    val postId: String = checkNotNull(savedStateHandle[POST_ID])

    companion object {
        const val POST_ID = "post_id"
    }
}