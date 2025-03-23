package com.example.ui.add_post


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "add_post"

fun NavController.navigateToAddPost(postTitle: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate("$ROUTE/$postTitle", builder)
}

fun NavGraphBuilder.addPostRoute(navController: NavHostController) {
    composable(
        route = "${ROUTE}/{${AddPostArgs.POST_TITLE_ARG}}",
        arguments = listOf(
            navArgument(AddPostArgs.POST_TITLE_ARG) { NavType.StringType },
        )
    ) { AddPostScreen(navController) }
}


class AddPostArgs(savedStateHandle: SavedStateHandle) {
    val postTitle: String = checkNotNull(savedStateHandle[POST_TITLE_ARG])

    companion object {
        const val POST_TITLE_ARG = "post_title"
    }
}