package com.example.ui.profile

import com.example.domain.model.PostItem
import com.example.domain.model.User
import com.example.ui.base.BaseUiState
import com.example.ui.util.empty

data class ProfileUiState(
    val id: String = String.empty(),
    val profileInformationUiState: ProfileInformationUiState = ProfileInformationUiState(),
    val userPosts: List<PostItemUiState> = emptyList(),
    val profileSettingsUiState: ProfileSettingsUiState = ProfileSettingsUiState(),
    val pagerNumber: Int = 0,
    val profileError: ProfileErrorUiState = ProfileErrorUiState(),
    val baseUiState: BaseUiState = BaseUiState()
)

data class ProfileInformationUiState(
    val imageUri: String = String.empty(),
    val name: String = String.empty(),
    val phone: String = String.empty(),
    val postsNumber: String = String.empty(),
    val location: String = String.empty(),
    val bio: String = String.empty(),
    val offersNumber: String = String.empty(),
    val exchangesNumber: String = String.empty(),
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
    val offersNumber: Int = 0
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

fun User.toProfileUiState(): ProfileUiState {
    return ProfileUiState(
        id = this.uuid,
        profileInformationUiState = ProfileInformationUiState(
            imageUri = this.imageLink,
            name = this.name,
            phone = this.phone,
            postsNumber = this.postsNumber.toString(),
            location = this.place,
            bio = this.bio,
            offersNumber = this.offersNumber.toString(),
        )
    )
}

fun PostItem.toPostItemUIState(): PostItemUiState {
    return PostItemUiState(
        id = this.uuid,
        username = this.user.name,
        userImageLink = this.user.imageLink,
        postImageLink = this.imageLink,
        isThePostOpen = this.isOpen,
        postTitle = this.title,
        postDescription = this.details,
        offersNumber = this.offers.size
    )
}


