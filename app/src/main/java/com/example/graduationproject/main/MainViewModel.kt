package com.example.graduationproject.main

import androidx.lifecycle.viewModelScope
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase
) : BaseViewModel<Boolean>(false) {
    init { viewModelScope.launch(Dispatchers.IO) { isDarkThem() } }


    private suspend fun isDarkThem() {
        customizeProfileSettings.isDarkThem().collect{ isDark ->
            _state.update { isDark }
        }
    }
}