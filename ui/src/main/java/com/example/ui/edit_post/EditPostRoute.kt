package com.example.ui.edit_post


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "edit_post"

fun NavController.navigateToEditPost(postId: String) {
    navigate("$ROUTE/$postId")
}

fun NavGraphBuilder.editPostRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${EditPostArgs.POST_ID_ARG}}",
        arguments = listOf(
            navArgument(EditPostArgs.POST_ID_ARG) { NavType.StringType },
        )
    ) { EditPostScreen(navController) }
}


class EditPostArgs(savedStateHandle: SavedStateHandle) {
    val postId: String = checkNotNull(savedStateHandle[POST_ID_ARG])

    companion object {
        const val POST_ID_ARG = "post_id"
    }
}