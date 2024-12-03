package com.example.ui.otp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun OtpScreen(navController: NavController, viewModel: OtpViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    OtpContent(
        uiState = uiState,
        onOtpChange = viewModel::onOtpChange,
        onClickConfirm = viewModel::onClickConfirm,
    )
}
