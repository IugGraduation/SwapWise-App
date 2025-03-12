package com.example.ui.reset_password

import com.example.ui.base.BaseUiState
import com.example.ui.util.empty

data class ResetPasswordUiState(
    val currentPassword: String = String.empty(),
    val newPassword: String = String.empty(),
    val confirmNewPassword: String = String.empty(),
    val resetPasswordErrorUiState: ResetPasswordErrorUiState = ResetPasswordErrorUiState(),
    val resetPasswordVisibilityUiState: ResetPasswordVisibilityUiState = ResetPasswordVisibilityUiState(),
    val baseUiState: BaseUiState = BaseUiState()
)

data class ResetPasswordErrorUiState (
    val currentPasswordErrorMessage: String = String.empty(),
    val newPasswordErrorMessage: String = String.empty(),
    val confirmNewPasswordErrorMessage: String = String.empty(),
)

data class ResetPasswordVisibilityUiState(
    val isCurrentPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmNewPasswordVisible: Boolean = false,
)