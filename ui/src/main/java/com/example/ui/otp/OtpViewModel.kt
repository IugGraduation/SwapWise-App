package com.example.ui.otp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(OtpUIState())
    val uiState = _uiState.asStateFlow()


    fun onOtpChange(newOtp: String) {
        _uiState.update { it.copy(otp = newOtp) }
        _uiState.update { it.copy(isConfirmButtonEnabled = newOtp.length == it.otpLength) }
    }


    fun onClickConfirm(){

    }
}

