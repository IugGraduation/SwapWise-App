package com.example.ui.confirm_number

import com.example.domain.OtpValidationUseCase
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    private val otpValidationUseCase: OtpValidationUseCase
) : BaseViewModel<ConfirmNumberUiState>(ConfirmNumberUiState()), IConfirmNumberInteractions {

    override fun onOtpChange(newOtp: String) {
        updateData {
            copy(
                otp = newOtp,
                isConfirmButtonEnabled = newOtp.length == otpLength
            )
        }

    }


    override fun onClickConfirm() {
        tryToExecute(
            call = {
                otpValidationUseCase(
                    otp = state.value.data.otp,
                )
            },
            onSuccess = { navigateToHome() },
        )
    }

    private fun navigateToHome() {
        updateData {
            copy(shouldNavigateToHome = true)
        }
    }

}

