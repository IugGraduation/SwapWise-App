package com.example.ui.topic_see_all


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "topic_see_all"

fun NavController.navigateToTopicSeeAll(topicType: String) {
    navigate("$ROUTE/$topicType")
}

fun NavGraphBuilder.topicSeeAllRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${TopicSeeAllArgs.TOPIC_TYPE_ARG}}",
        arguments = listOf(
            navArgument(TopicSeeAllArgs.TOPIC_TYPE_ARG) { NavType.StringType },
        )
    ) { TopicSeeAllScreen(navController) }
}


class TopicSeeAllArgs(savedStateHandle: SavedStateHandle) {
    val topicType: String = checkNotNull(savedStateHandle[TOPIC_TYPE_ARG])

    companion object {
        const val TOPIC_TYPE_ARG = "topicType"
    }
}