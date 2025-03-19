package com.example.ui.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import com.example.domain.model.PostItem
import com.example.domain.post.GetImageRequestBodyUseCase
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.domain.profile.GetCurrentUserDataUseCase
import com.example.domain.profile.GetCurrentUserPostsUseCase
import com.example.domain.profile.LogoutUseCase
import com.example.domain.profile.UpdateUserInfoUseCase
import com.example.ui.base.BaseUiState
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
    private val getImageRequestBodyUseCase: GetImageRequestBodyUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<ProfileUiState, ProfileEffect>(ProfileUiState()), ProfileInteraction {

    private lateinit var originalProfileInformation: ProfileInformationUiState

    init {
        viewModelScope.launch { isDarkTheme() }
        getLastSelectedAppLanguage()
        getCurrentUserInfo()
        getCurrentUserPosts()
    }

    private fun getLastSelectedAppLanguage() {
        viewModelScope.launch {
            customizeProfileSettings.getLatestSelectedAppLanguage().collect(){ language ->
                updateData { copy(profileSettingsUiState = profileSettingsUiState.copy(lastAppLanguage = language)) }
            }
        }
    }

    private fun getCurrentUserInfo() {
        tryToExecute(
            call = { getCurrentUserDataUseCase(getCurrentUserDataUseCase.getCurrentUserId()).toProfileUiState() },
            onSuccess = ::onGetCurrentUserSuccess,
            onError = ::onGetCurrentUserFail,
            shouldLoad = true,
            shouldHideContent = true
        )
    }

    private fun onGetCurrentUserSuccess(user: ProfileUiState) {
        updateData { copy(profileInformationUiState = user.profileInformationUiState) }
        originalProfileInformation = user.profileInformationUiState
    }

    private fun onGetCurrentUserFail(throwable: Throwable) =
        updateBaseErrorMessage(throwable.message)

    private fun getCurrentUserPosts() {
        tryToExecute(
            call = { getCurrentUserPostsUseCase() },
            onSuccess = ::onGetCurrentUserPostsSuccess,
            onError = ::onGetCurrentUserPostsFail
        )
    }

    private fun onGetCurrentUserPostsSuccess(postItems: List<PostItem>) {
        updateData { copy(userPosts = postItems.map { it.toPostItemUIState() }) }
    }


    private fun onGetCurrentUserPostsFail(throwable: Throwable) =
        updateBaseErrorMessage(throwable.message)

    override fun onUpdateProfileImage(imageUri: Uri) {
        updateData {
            copy(
                profileInformationUiState = _state.value.data.profileInformationUiState.copy(
                    imageUri = imageUri.toString()
                )
            )
        }
    }

    override fun onEditButtonClicked() = manageUserInfoEdit(isEditable = true)

    override fun onUsernameChange(newName: String) = updateProfileField { copy(name = newName) }

    override fun onPhoneNumberChange(newNumber: String) =
        updateProfileField { copy(phoneNumber = newNumber) }

    override fun onLocationChange(location: String) =
        updateProfileField { copy(location = location) }

    override fun onBioChange(bio: String) = updateProfileField { copy(bio = bio) }

    private fun updateProfileField(update: ProfileInformationUiState.() -> ProfileInformationUiState) {
        updateData { copy(profileInformationUiState = profileInformationUiState.update()) }
    }

    override fun onCancelButtonClicked() {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()
        updateData { copy(profileInformationUiState = originalProfileInformation) }
    }

    override fun onSaveButtonClicked() {
        val lastUserInfo = _state.value.data.profileInformationUiState
        tryToExecute(
            call = {
                updateUserInfoUseCase(
                    name = lastUserInfo.name,
                    phoneNumber = lastUserInfo.phoneNumber,
                    location = lastUserInfo.location,
                    imageRequestBody = getImageRequestBodyUseCase(
                        _state.value.data.profileInformationUiState.imageUri.toUri(),
                        acceptNull = true
                    ),
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
        if (isUpdated) getCurrentUserInfo()
    }

    private fun onUpdateUserInfoFail(throwable: Throwable) {
        when (throwable) {
            is InvalidUsernameException -> updateFieldError(userNameError = stringsResource.invalidUsername)
            is InvalidPhoneNumberException -> updateFieldError(phoneNumberError = stringsResource.invalidPhoneNumber)
            is InvalidLocationException -> updateFieldError(locationError = stringsResource.invalidLocation)
            else -> updateFieldError()
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
            onError = ::onLogoutFail,
        )
    }

    private fun onLogoutSuccess() = sendUiEffect(ProfileEffect.NavigateToLoginScreen)

    private fun onLogoutFail(throwable: Throwable) {
        updateBaseErrorMessage(throwable.message)
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
        updateData {
            copy(
                profileInformationUiState = profileInformationUiState.copy(
                    isUserInfoEditable = isEditable
                )
            )
        }
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

    fun updatePagerNumber(currentPage: Int) {
        updateData {
            copy(pagerNumber = currentPage)
        }
    }

    private fun updateBaseErrorMessage(message: String?) {
        updateData {
            copy(
                baseUiState = BaseUiState(
                    errorMessage = message ?: stringsResource.globalMessageError
                )
            )
        }
    }
}