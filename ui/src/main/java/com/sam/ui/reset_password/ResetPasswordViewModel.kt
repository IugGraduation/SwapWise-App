package com.sam.ui.reset_password

import com.sam.domain.exception.EmptyConfirmPasswordException
import com.sam.domain.exception.EmptyNewPasswordException
import com.sam.domain.exception.EmptyPasswordException
import com.sam.domain.exception.InvalidConfirmPasswordException
import com.sam.domain.exception.InvalidNewPasswordException
import com.sam.domain.exception.InvalidPasswordException
import com.sam.domain.exception.PasswordMismatchException
import com.sam.domain.exception.SamePasswordException
import com.sam.domain.profile.ResetPasswordUseCase
import com.sam.ui.base.BaseViewModel
import com.sam.ui.base.StringsResource
import com.sam.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val stringsResource: StringsResource
    ) : BaseViewModel<ResetPasswordUiState, ResetPasswordEffect>(ResetPasswordUiState()),
    ResetPasswordInteraction {

    override fun onPopUpToPreviousScreen() = sendUiEffect(ResetPasswordEffect.PopUpToPreviousScreen)

    override fun onCurrentPasswordChange(password: String) =
        updateData { copy(currentPassword = password) }

    override fun onNewPasswordChange(password: String) = updateData { copy(newPassword = password) }

    override fun onConfirmNewPasswordChange(password: String) =
        updateData { copy(confirmNewPassword = password) }


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

    override fun onResetPasswordClick() {
        tryToExecute(
            call = {
                resetPasswordUseCase(
                    currentPassword = _state.value.data.currentPassword,
                    newPassword = _state.value.data.newPassword,
                    confirmNewPassword = _state.value.data.confirmNewPassword
                )
            },
            onSuccess = { onResetPasswordSuccess() },
            onError = ::onResetPasswordFail
        )
    }


    private fun onResetPasswordSuccess() {
        updateErrorState()
        updateData { copy(isSuccessDialogVisible = true) }
    }

    override fun onDismissSuccessDialog() {
        updateData { copy(isSuccessDialogVisible = false) }
        sendUiEffect(ResetPasswordEffect.PopUpToPreviousScreen)
    }

    private fun onResetPasswordFail(throwable: Throwable) {
        when (throwable) {
            is PasswordMismatchException -> {
                val mismatchErrorMessage = stringsResource.passwordMismatch
                updateErrorState(
                    newPasswordError = mismatchErrorMessage,
                    confirmNewPasswordError = mismatchErrorMessage
                )
            }

            is InvalidPasswordException -> {
                updateErrorState(currentPasswordError = stringsResource.invalidPassword)
            }

            is InvalidNewPasswordException -> {
                updateErrorState(newPasswordError = stringsResource.invalidNewPassword)
            }

            is InvalidConfirmPasswordException -> {
                updateErrorState(confirmNewPasswordError = stringsResource.invalidConfirmPassword)
            }

            is EmptyPasswordException -> {
                updateErrorState(currentPasswordError = stringsResource.emptyPassword)
            }

            is EmptyNewPasswordException -> {
                updateErrorState(newPasswordError = stringsResource.emptyNewPassword)
            }

            is EmptyConfirmPasswordException -> {
                updateErrorState(confirmNewPasswordError = stringsResource.emptyConfirmPassword)
            }

            is SamePasswordException -> {
                val samePasswordErrorMessage = stringsResource.sameConfirmPassword
                updateErrorState(
                    currentPasswordError = samePasswordErrorMessage,
                    newPasswordError = samePasswordErrorMessage
                )
            }

            else -> onActionFail(throwable)
        }
    }

    private fun updateErrorState(
        currentPasswordError: String = String.empty(),
        newPasswordError: String = String.empty(),
        confirmNewPasswordError: String = String.empty()
    ) {
        updateData {
            copy(
                resetPasswordErrorUiState = ResetPasswordErrorUiState().copy(
                    currentPasswordErrorMessage = currentPasswordError,
                    newPasswordErrorMessage = newPasswordError,
                    confirmNewPasswordErrorMessage = confirmNewPasswordError
                )
            )
        }
    }
}