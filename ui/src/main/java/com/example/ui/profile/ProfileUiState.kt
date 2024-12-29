package com.example.ui.profile

import com.example.ui.util.empty

data class ProfileUiState(
    val id: Int = 0,
    val imageUrl: String = String.empty(),
    val name: String = String.empty(),
    val bio: String = String.empty(),
    val email: String = String.empty(),
    val message: String? = null,
    val languageMap: Map<String, String> = mapOf(
        LocalLanguage.English.name to "en",
        LocalLanguage.Arabic.name to "ar",
    ),
    val pagerNumber: Int = 0,
    var lastAppLanguage: String = "English",
    val showNoInternetLottie: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val showEditUsernameDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val newUsername: String = String.empty(),
    val isDarkTheme: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)


