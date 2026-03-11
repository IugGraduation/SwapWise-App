package com.sam.swapwise.main

import androidx.lifecycle.viewModelScope
import com.sam.domain.profile.CustomizeProfileSettingsUseCase
import com.sam.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase
) : BaseViewModel<Boolean, Nothing>(false) {
    init { viewModelScope.launch(Dispatchers.IO) { isDarkTheme() } }

    private suspend fun isDarkTheme() {
        customizeProfileSettings.isDarkThem().collect{ isDark ->
            updateData { isDark }
        }
    }

    fun getLastSelectedAppLanguage() =
        customizeProfileSettings.getLatestSelectedAppLanguage()
}