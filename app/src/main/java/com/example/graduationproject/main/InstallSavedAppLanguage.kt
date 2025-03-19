package com.example.graduationproject.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.components.atoms.updateResources
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@Composable
fun InstallSavedAppLanguage(context: Context){
    val userSettingsViewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit){
        userSettingsViewModel.getLastSelectedAppLanguage().collectLatest {language ->
            updateResources(context = context, localeLanguage = Locale(language))
        }
    }
}