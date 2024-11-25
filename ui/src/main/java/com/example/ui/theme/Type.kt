package com.example.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf


val LocalCustomTypography = staticCompositionLocalOf { TextStyles }

//object CustomTypography {
//    val headingExtraLarge: TextStyle
//        @Composable
//        get() = LocalCustomTypography.current.headingExtraLarge
//
//    val headingLarge: TextStyle
//        @Composable
//        get() = LocalCustomTypography.current.headingLarge
//
//    // Add getters for all your styles
//}