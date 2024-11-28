package com.example.ui.shared

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedAuthViewModel @Inject constructor(): ViewModel() {
    private val _isPasswordVisible = MutableStateFlow(false)
    var isPasswordVisible = _isPasswordVisible.asStateFlow()

    fun togglePasswordVisibility() {
        _isPasswordVisible.update { !it }
    }


}