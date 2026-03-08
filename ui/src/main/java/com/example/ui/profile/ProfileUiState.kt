package com.example.ui.profile

import com.example.domain.model.LocationItem
import com.example.domain.model.PostItem
import com.example.domain.model.User
import com.example.ui.base.BaseUiState
import com.example.ui.models.AsyncState
import com.example.ui.util.empty

data class ProfileUiState(
    val userInformation: AsyncState<ProfileInformationUiState> = AsyncState.Initial,
    val userPosts: AsyncState<List<PostItemUiState>> = AsyncState.Initial,
    val profileSettingsUiState: ProfileSettingsUiState = ProfileSettingsUiState(),
    val profileError: ProfileErrorUiState = ProfileErrorUiState(),
    val baseUiState: BaseUiState = BaseUiState(),
)

data class ProfileInformationUiState(
    val imageUri: String = String.empty(),
    val name: String = String.empty(),
    val phone: String = String.empty(),
    val locationDropdown: AsyncState<List<LocationItem>> = AsyncState.Initial,
    val selectedLocation: LocationItem? = null,
    val bio: String = String.empty(),
    val isUserInfoEditable: Boolean = false,
)

data class PostItemUiState(
    val id: String = String.empty(),
    val username: String = String.empty(),
    val userImageLink: String = String.empty(),
    val postImageLink: String = String.empty(),
    val isThePostOpen: Boolean = false,
    val postTitle: String = String.empty(),
    val postDescription: String = String.empty(),
    val postLocation: LocationItem = LocationItem(),
)

data class ProfileSettingsUiState(
    val isDarkTheme: Boolean = false,
    val languageMap: Map<String, String> = mapOf(
        LocalLanguage.English.name to "en",
        LocalLanguage.Arabic.name to "ar",
    ),
    var lastAppLanguage: String = "English",
    val showLanguageDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
)

data class ProfileErrorUiState(
    val userNameErrorMessage: String = String.empty(),
    val phoneNumberErrorMessage: String = String.empty(),
    val locationErrorMessage: String = String.empty(),
    val bioErrorMessage: String = String.empty(),
)

fun User.toProfileInformationUiState(): ProfileInformationUiState {
    return ProfileInformationUiState(
        imageUri = this.imageLink,
        name = this.name,
        phone = this.phone,
        selectedLocation = LocationItem(id = this.locationId),
        bio = this.bio,
    )
}

fun PostItem.toPostItemUIState(): PostItemUiState {
    return PostItemUiState(
        id = this.id,
        username = this.user.name,
        userImageLink = this.user.imageLink,
        postImageLink = this.imageUrl,
        isThePostOpen = this.isOpen,
        postTitle = this.name,
        postDescription = this.details,
        postLocation = this.locationItem
    )
}
