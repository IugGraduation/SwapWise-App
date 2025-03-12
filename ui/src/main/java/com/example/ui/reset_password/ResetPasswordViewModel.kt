package com.example.ui.reset_password

import com.example.domain.exception.EmptyConfirmPasswordException
import com.example.domain.exception.EmptyNewPasswordException
import com.example.domain.exception.EmptyPasswordException
import com.example.domain.exception.InvalidConfirmPasswordException
import com.example.domain.exception.InvalidNewPasswordException
import com.example.domain.exception.InvalidPasswordException
import com.example.domain.exception.PasswordMismatchException
import com.example.domain.exception.SamePasswordException
import com.example.domain.profile.ResetPasswordUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.StringsResource
import com.example.ui.util.empty
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
        //todo: show dialog with success state
        updateErrorState()
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

            else -> {
                updateData { copy(baseUiState = baseUiState.copy(errorMessage = throwable.message.toString())) }
            }
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