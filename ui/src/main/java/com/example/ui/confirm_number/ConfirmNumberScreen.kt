package com.example.ui.confirm_number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun OtpScreen(navController: NavController, viewModel: ConfirmNumberViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    OtpContent(
        state = state,
        onOtpChange = viewModel::onOtpChange,
        onClickConfirm = viewModel::onClickConfirm,
    )
}
