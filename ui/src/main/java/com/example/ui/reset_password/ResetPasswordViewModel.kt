package com.example.ui.reset_password

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor() : BaseViewModel<ResetPasswordUiState, ResetPasswordEffect>(
    ResetPasswordUiState()
), ResetPasswordInteraction {
    override fun onPopUpToPreviousScreen() {
        sendUiEffect(ResetPasswordEffect.PopUpToPreviousScreen)
    }

    override fun onCurrentPasswordChange(password: String) {
        updateData { copy(currentPassword = password) }
    }

    override fun onNewPasswordChange(password: String) {
        updateData { copy(newPassword = password) }
    }

    override fun onConfirmNewPasswordChange(password: String) {
        updateData { copy(confirmNewPassword = password) }
    }

    override fun toggleCurrentPasswordVisibility() {
        updateData {
            copy(
                resetPasswordVisibilityUiState = resetPasswordVisibilityUiState.copy(
                    isCurrentPasswordVisible = !resetPasswordVisibilityUiState.isCurrentPasswordVisible
                )
            )
        }
    }

    override fun toggleNewPasswordVisibility() {
        updateData {
            copy(
                resetPasswordVisibilityUiState = resetPasswordVisibilityUiState.copy(
                    isNewPasswordVisible = !resetPasswordVisibilityUiState.isNewPasswordVisible
                )
            )
        }
    }

    override fun toggleConfirmNewPasswordVisibility() {
        updateData {
            copy(
                resetPasswordVisibilityUiState = resetPasswordVisibilityUiState.copy(
                    isConfirmNewPasswordVisible = !resetPasswordVisibilityUiState.isConfirmNewPasswordVisible
                )
            )
        }
    }


}