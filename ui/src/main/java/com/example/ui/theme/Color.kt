package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF0065FF)
val Secondary = Color(0xFFB3CBFF)
val Tertiary = Color(0xFFE5EFFF)

val Danger = Color(0xFFEA4335)
val Correct = Color(0xFF7EBA18)
val Gray = Color(0xFFEEEEEE)

val PrimaryOverlay = Color(0x330065FF)
val DangerOverlay = Color(0x3DFF0505)
val CorrectOverlay = Color(0x3E8FE300)

val Purple = Color(0xFF9000FF)


// Light mode
val BackgroundLight = Color(0xFFF9F7FF)
val OnBackgroundLight = Color(0xFFFFFFFF)

val BlackPrimary = Color(0xE0000000)
val BlackSecondary = Color(0xAD000000)
val BlackTertiary = Color(0x7A000000)
val BlackFourth = Color(0x33000000)


// Dark mode
val BackgroundDark = Color(0xFF111111)
val OnBackgroundDark = Color(0xFF0D0D0D)

val WhitePrimary = Color(0xFFFFFFFF)
val WhiteSecondary = Color(0xADFFFFFF)
val WhiteTertiary = Color(0x7AFFFFFF)
val WhiteFourth = Color(0x33FFFFFF)

val GradientBrush = Brush.horizontalGradient(listOf(Purple, Primary))
val DiameterGradientBrush = Brush.linearGradient(
    listOf(Purple, Primary),
    start = androidx.compose.ui.geometry.Offset(0f, 1000f), // Bottom left
    end = androidx.compose.ui.geometry.Offset(1000f, 0f) // Top right
)
@Immutable
data class CustomColorsPalette(
    val primary: Color = Primary,
    val secondary: Color = Secondary,
    val tertiary: Color = Tertiary,
    val danger: Color = Danger,
    val correct: Color = Correct,
    val gray: Color = Gray,
    val transparent: Color = Color.Transparent,
    val primaryOverlay: Color = PrimaryOverlay,
    val dangerOverlay : Color = DangerOverlay,
    val correctOverlay: Color = CorrectOverlay,
    val purple: Color = Purple,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val textPrimary: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val textTertiary: Color = Color.Unspecified,
    val textFourth: Color = Color.Unspecified,
)

val onLightCustomColorsPalette = CustomColorsPalette(
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    textPrimary = BlackPrimary,
    textSecondary = BlackSecondary,
    textTertiary = BlackTertiary,
    textFourth = BlackFourth
)

val onDarkCustomColorsPalette = CustomColorsPalette(
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    textPrimary = WhitePrimary,
    textSecondary = WhiteSecondary,
    textTertiary = WhiteTertiary,
    textFourth = WhiteFourth
)

val LocalColor = compositionLocalOf { CustomColorsPalette() }

val MaterialTheme.color: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalColor.current
