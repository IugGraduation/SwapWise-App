package com.sam.ui.reset_password

import com.sam.ui.base.BaseUiState
import com.sam.ui.util.empty

data class ResetPasswordUiState(
    val currentPassword: String = String.empty(),
    val newPassword: String = String.empty(),
    val confirmNewPassword: String = String.empty(),
    val resetPasswordErrorUiState: ResetPasswordErrorUiState = ResetPasswordErrorUiState(),
    val resetPasswordVisibilityUiState: ResetPasswordVisibilityUiState = ResetPasswordVisibilityUiState(),
    val isSuccessDialogVisible: Boolean = false,
    val baseUiState: BaseUiState = BaseUiState(),
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
