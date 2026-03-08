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
import com.example.ui.models.AsyncState
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
        getLocations()
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
        tryToExecuteAsync(
            call = { getCurrentUserDataUseCase() },
            stateUpdater = { newState ->
                val infoState = newState.mapData { it.toProfileInformationUiState() }
                updateData { copy(userInformation = infoState) }
                if (infoState is AsyncState.Success) {
                    originalProfileInformation = infoState.data
                    
                    // Try to resolve location name if dropdown data is already available
                    val currentLocations = _state.value.data.userInformation.data?.locationDropdown
                    if (currentLocations is AsyncState.Success) {
                        resolveSelectedLocation(currentLocations.data)
                    }
                }
            }
        )
    }

    private fun getLocations() {
        tryToExecuteAsync(
            call = { getLocationsUseCase() },
            stateUpdater = { newState ->
                updateProfileInfo { copy(locationDropdown = newState) }
                if (newState is AsyncState.Success) {
                    resolveSelectedLocation(newState.data)
                }
            }
        )
    }

    private fun resolveSelectedLocation(locations: List<LocationItem>) {
        updateProfileInfo {
            val resolved = locations.find { it.id == selectedLocation?.id }
            copy(selectedLocation = resolved ?: selectedLocation)
        }
    }

    private fun getCurrentUserPosts() {
        tryToExecuteAsync(
            call = { getCurrentUserPostsUseCase() },
            stateUpdater = { newState ->
                updateData { copy(userPosts = newState.mapData { list -> list.map { it.toPostItemUIState() } }) }
            }
        )
    }

    override fun onUpdateProfileImage(imageUri: Uri) {
        updateProfileInfo { copy(imageUri = imageUri.toString()) }
    }

    override fun onEditButtonClicked() = manageUserInfoEdit(isEditable = true)

    override fun onUsernameChange(newName: String) = updateProfileInfo { copy(name = newName) }

    override fun onPhoneNumberChange(newNumber: String) =
        updateProfileInfo { copy(phone = newNumber) }

    override fun onLocationChange(location: LocationItem) {
        updateProfileInfo { copy(selectedLocation = location) }
    }

    override fun onBioChange(bio: String) = updateProfileInfo { copy(bio = bio) }

    private fun updateProfileInfo(update: ProfileInformationUiState.() -> ProfileInformationUiState) {
        updateData { copy(userInformation = userInformation.mapData { it.update() }) }
    }

    override fun onCancelButtonClicked() {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()
        updateData { copy(userInformation = AsyncState.Success(originalProfileInformation)) }
    }

    override fun onSaveButtonClicked(imageByteArray: ByteArray?) {
        val lastUserInfo = _state.value.data.userInformation.data ?: return
        tryToExecute(
            call = {
                updateUserInfoUseCase(
                    name = lastUserInfo.name,
                    phone = lastUserInfo.phone,
                    locationId = lastUserInfo.selectedLocation?.id.orEmpty(),
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
            onSuccess = ::onLogoutSuccess,
        )
    }

    private fun onLogoutSuccess() = sendUiEffect(ProfileEffect.NavigateToLoginScreen)

    override fun onLogoutRetry() {
        onLogoutClicked()
    }

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
        getLocations()
    }

    override fun onRetryUserPosts() {
        getCurrentUserPosts()
    }

    override fun initUserDataRetry() {
        initUserData()
    }

    private fun onUpdateLanguageSuccess() {
        updateLanguageDialogState(false)
    }

    private fun onUpdateLanguageFail(throwable: Throwable) {
        updateLanguageDialogState(false)
    }

    override fun onUpdateLogoutDialogState(showDialog: Boolean) {
        updateData {
            copy(
                profileSettingsUiState = _state.value.data.profileSettingsUiState.copy(
                    showLogoutDialog = showDialog
                )
            )
        }
    }

    private suspend fun isDarkTheme() {
        customizeProfileSettings.isDarkThem().buffer().collect { isDark ->
            updateData {
                copy(profileSettingsUiState = profileSettingsUiState.copy(isDarkTheme = isDark))
            }
        }
    }

    private fun manageUserInfoEdit(isEditable: Boolean) {
        updateProfileInfo { copy(isUserInfoEditable = isEditable) }
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
