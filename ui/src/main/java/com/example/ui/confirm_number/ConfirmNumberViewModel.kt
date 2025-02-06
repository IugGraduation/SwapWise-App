package com.example.ui.confirm_number

import com.example.domain.OtpValidationUseCase
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    private val otpValidationUseCase: OtpValidationUseCase
) : BaseViewModel<ConfirmNumberUiState>(ConfirmNumberUiState()), IConfirmNumberInteractions {

    override fun onOtpChange(newOtp: String) {
        _state.update {
            it.copy(
                otp = newOtp,
                isConfirmButtonEnabled = newOtp.length == it.otpLength
            )
        }
    }


    override fun onClickConfirm() {
        tryToExecute(
            call = {
                otpValidationUseCase(
                    otp = state.value.otp,
                )
            },
            onSuccess = { navigateToHome() },
        )
    }

    private fun navigateToHome() {
        _state.update { it.copy(shouldNavigateToHome = true) }
    }

}

