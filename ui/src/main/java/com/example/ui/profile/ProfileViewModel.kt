package com.example.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import com.example.domain.location.GetLocationsUseCase
import com.example.domain.model.LocationItem
import com.example.domain.model.PostItem
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.domain.profile.GetCurrentUserDataUseCase
import com.example.domain.profile.GetCurrentUserPostsUseCase
import com.example.domain.profile.LogoutUseCase
import com.example.domain.profile.UpdateUserInfoUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.base.StringsResource
import com.example.ui.util.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val stringsResource: StringsResource,
    private val getCurrentUserDataUseCase: GetCurrentUserDataUseCase,
    private val getCurrentUserPostsUseCase: GetCurrentUserPostsUseCase,
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getLocationsUseCase: GetLocationsUseCase
) : BaseViewModel<ProfileUiState, ProfileEffect>(ProfileUiState()), ProfileInteraction {

    private var originalProfileInformation: ProfileInformationUiState = ProfileInformationUiState()

    init {
        viewModelScope.launch { isDarkTheme() }
        getLastSelectedAppLanguage()
        initUserData()
        getCurrentUserPosts()
    }

    private fun getLastSelectedAppLanguage() {
        viewModelScope.launch {
            customizeProfileSettings.getLatestSelectedAppLanguage().collect { language ->
                updateData {
                    copy(profileSettingsUiState = profileSettingsUiState.copy(lastAppLanguage = language))
                }
            }
        }
    }

    private fun initUserData() {
        tryToExecute(
            call = {
                val locations = fetchLocations()
                val userState = getCurrentUserDataUseCase().toProfileUiState()

                // Find the full LocationItem with name using the ID from user data
                val selectedId =
                    userState.profileInformationUiState.locationDropdown.selectedItem?.id
                val fullLocation = locations.find { it.id == selectedId }

                userState.profileInformationUiState.copy(
                    locationDropdown = userState.profileInformationUiState.locationDropdown.copy(
                        selectedItem = fullLocation
                            ?: userState.profileInformationUiState.locationDropdown.selectedItem,
                        items = locations,
                    )
                )
            },
            onSuccess = { profileInfo ->
                updateData { copy(profileInformationUiState = profileInfo) }
                originalProfileInformation = profileInfo
            },
            shouldLoad = true,
            shouldHideContent = true
        )
    }

    private suspend fun fetchLocations(): List<LocationItem> {
        updateProfileField {
            copy(
                locationDropdown = locationDropdown.copy(
                    isLoading = true,
                    error = null
                )
            )
        }
        return try {
            val locations = getLocationsUseCase()
            updateProfileField {
                copy(
                    locationDropdown = locationDropdown.copy(
                        items = locations,
                        isLoading = false
                    )
                )
            }
            locations
        } catch (e: Exception) {
            updateProfileField {
                copy(
                    locationDropdown = locationDropdown.copy(
                        isLoading = false,
                        error = e.message
                    )
                )
            }
            emptyList()
        }
    }

    private fun getCurrentUserPosts() {
        tryToExecute(
            call = { getCurrentUserPostsUseCase() },
            onSuccess = ::onGetCurrentUserPostsSuccess,
        )
    }

    private fun onGetCurrentUserPostsSuccess(postItems: List<PostItem>) {
        updateData { copy(userPosts = postItems.map { it.toPostItemUIState() }) }
    }

    override fun onUpdateProfileImage(imageUri: Uri) {
        updateProfileField { copy(imageUri = imageUri.toString()) }
    }

    override fun onEditButtonClicked() = manageUserInfoEdit(isEditable = true)

    override fun onUsernameChange(newName: String) = updateProfileField { copy(name = newName) }

    override fun onPhoneNumberChange(newNumber: String) =
        updateProfileField { copy(phone = newNumber) }

    override fun onLocationChange(location: LocationItem) {
        updateProfileField { copy(locationDropdown = locationDropdown.copy(selectedItem = location)) }
    }

    override fun onBioChange(bio: String) = updateProfileField { copy(bio = bio) }

    private fun updateProfileField(update: ProfileInformationUiState.() -> ProfileInformationUiState) {
        updateData { copy(profileInformationUiState = profileInformationUiState.update()) }
    }

    override fun onCancelButtonClicked() {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()
        updateData { copy(profileInformationUiState = originalProfileInformation) }
    }

    override fun onSaveButtonClicked(imageByteArray: ByteArray?) {
        val lastUserInfo = _state.value.data.profileInformationUiState
        tryToExecute(
            call = {
                updateUserInfoUseCase(
                    name = lastUserInfo.name,
                    phone = lastUserInfo.phone,
                    locationId = lastUserInfo.locationDropdown.selectedItem?.id.orEmpty(),
                    imageByteArray = imageByteArray,
                    bio = lastUserInfo.bio
                )
            },
            onSuccess = ::onUpdateUserInfoSuccess,
            onError = ::onUpdateUserInfoFail,
        )
    }

    private fun onUpdateUserInfoSuccess(isUpdated: Boolean) {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()
        if (isUpdated) initUserData()
    }

    private fun onUpdateUserInfoFail(throwable: Throwable) {
        when (throwable) {
            is InvalidUsernameException -> updateFieldError(userNameError = stringsResource.invalidUsername)
            is InvalidPhoneNumberException -> updateFieldError(phoneNumberError = stringsResource.invalidPhoneNumber)
            is InvalidLocationException -> updateFieldError(locationError = stringsResource.invalidLocation)
            else -> updateFieldError().also { onActionFail(throwable) }
        }
    }

    override fun onDarkMoodChange(isDarkMood: Boolean) {
        updateData { copy(profileSettingsUiState = profileSettingsUiState.copy(isDarkTheme = isDarkMood)) }
        viewModelScope.launch(Dispatchers.IO) { customizeProfileSettings.updateDarkTheme(isDarkMood) }
    }

    override fun onLogoutClicked() {
        tryToExecute(
            call = logoutUseCase::invoke,
            onSuccess = { onLogoutSuccess() },
        )
    }

    private fun onLogoutSuccess() = sendUiEffect(ProfileEffect.NavigateToLoginScreen)

    override fun onResetPasswordClicked() = sendUiEffect(ProfileEffect.NavigateToResetPassword)

    override fun updateLanguageDialogState(showDialog: Boolean) {
        updateData {
            copy(
                profileSettingsUiState = _state.value.data.profileSettingsUiState.copy(
                    showLanguageDialog = showDialog
                )
            )
        }
    }

    override fun onUpdateLanguage(language: String) {
        updateData {
            copy(
                profileSettingsUiState = _state.value.data.profileSettingsUiState.copy(
                    lastAppLanguage = language
                )
            )
        }
        tryToExecute(
            call = { customizeProfileSettings.updateAppLanguage(language) },
            onSuccess = ::onUpdateLanguageSuccess,
            onError = ::onUpdateLanguageFail,
        )
    }

    override fun navigateToPostDetails(postId: String) {
        sendUiEffect(ProfileEffect.NavigateToPostDetails(postId))
    }

    override fun onRetryLocations() {
        initUserData()
    }

    private fun onUpdateLanguageSuccess() {
        updateLanguageDialogState(false)
    }

    private fun onUpdateLanguageFail(throwable: Throwable) {
        updateLanguageDialogState(false)
    }

    override fun onUpdateLogoutDialogState(showDialog: Boolean) {
        updateData { copy(profileSettingsUiState = ProfileSettingsUiState().copy(showLogoutDialog = showDialog)) }
    }

    private suspend fun isDarkTheme() {
        customizeProfileSettings.isDarkThem().buffer().collect { isDark ->
            updateData {
                copy(profileSettingsUiState = profileSettingsUiState.copy(isDarkTheme = isDark))
            }
        }
    }

    private fun manageUserInfoEdit(isEditable: Boolean) {
        updateProfileField { copy(isUserInfoEditable = isEditable) }
    }

    private fun updateFieldError(
        userNameError: String = String.empty(),
        phoneNumberError: String = String.empty(),
        locationError: String = String.empty(),
        bioError: String = String.empty(),
    ) {
        updateData {
            copy(
                profileError = ProfileErrorUiState(
                    userNameErrorMessage = userNameError,
                    phoneNumberErrorMessage = phoneNumberError,
                    locationErrorMessage = locationError,
                    bioErrorMessage = bioError
                )
            )
        }
    }

    private fun makeErrorMessagesEmpty() {
        updateData {
            copy(
                profileError = ProfileErrorUiState(
                    userNameErrorMessage = String.empty(),
                    phoneNumberErrorMessage = String.empty(),
                    locationErrorMessage = String.empty(),
                    bioErrorMessage = String.empty()
                )
            )
        }
    }
}
