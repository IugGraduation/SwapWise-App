package com.example.ui.confirm_number

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(ConfirmNumberUiState())
    val state = _state.asStateFlow()


    fun onOtpChange(newOtp: String) {
        _state.update { it.copy(otp = newOtp) }
        _state.update { it.copy(isConfirmButtonEnabled = newOtp.length == it.otpLength) }
    }


    fun onClickConfirm(){

    }
}

