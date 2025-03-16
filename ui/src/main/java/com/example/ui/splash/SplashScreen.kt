package com.example.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.components.templates.ScreenTemplate
import com.example.ui.home.navigateToHome
import com.example.ui.login.navigateToLogin
import com.example.ui.otp.navigateToOtp
import com.example.ui.theme.DiameterGradientBrush
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.TextStyles

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(splashViewModel.effect) {
        splashViewModel.effect.collect { effect ->
            when (effect) {
                SplashEffects.NavigateToHome -> navController.navigateToHome()

                SplashEffects.NavigateToOtp -> {
                    navController.navigateToLogin {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    navController.navigateToOtp()
                }

                SplashEffects.NavigateToLogin -> {
                    navController.navigateToLogin {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        }
    }
    SplashContent()
}

@Composable
fun SplashContent() {
    ScreenTemplate {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(DiameterGradientBrush)
        ) {
            Text(
                text = stringResource(R.string.swapwise),
                style = TextStyles.largeCustomTitle.copy(
                    fontSize = 54.sp,
                    drawStyle = Stroke(width = 5f, join = StrokeJoin.Miter)
                ),
                color = Color.White,
                modifier = Modifier.padding(horizontal = Spacing16)
            )
        }
    }
}


@Preview
@Composable
fun PreviewSplashContent() {
    GraduationProjectTheme {
        SplashContent()
    }
}