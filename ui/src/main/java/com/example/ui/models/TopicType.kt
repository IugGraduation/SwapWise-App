package com.example.ui.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.ui.R

enum class TopicType(val resId: Int) {
    Categories(R.string.categories),
    TopInteractive(R.string.top_interactive),
    RecentPosts(R.string.recent_posts),
}

@Composable
fun TopicType.getName(): String {
    return LocalContext.current.getString(resId)
}