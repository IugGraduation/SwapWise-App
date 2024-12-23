package com.example.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.ui.models.TopicType


@Composable
fun TopicType.getName(): String {
    return LocalContext.current.getString(resId)
}

