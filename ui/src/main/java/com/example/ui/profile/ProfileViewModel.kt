package com.example.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.domain.exception.InvalidLocationException
import com.example.domain.exception.InvalidPhoneNumberException
import com.example.domain.exception.InvalidUsernameException
import com.example.domain.profile.CustomizeProfileSettingsUseCase
import com.example.domain.profile.GetCurrentUserDataUseCase
import com.example.domain.profile.ValidateUserInfoUseCase
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
    private val getCurrentUserData: GetCurrentUserDataUseCase,
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase,
    private val validateUserInfoUseCase: ValidateUserInfoUseCase,
) : BaseViewModel<ProfileUiState, ProfileEffect>(ProfileUiState()), ProfileInteraction {

    init {
        getCurrentUserInfo()
        viewModelScope.launch { isDarkTheme() }
    }

    private fun getCurrentUserInfo() {
        tryToExecute(
            call = { getCurrentUserData(getCurrentUserData.getCurrentUserId()).toProfileUiState() },
            onSuccess = ::onGetCurrentUserSuccess,
            onError = ::onGetCurrentUserFail

        )
    }

    private fun onGetCurrentUserSuccess(user: ProfileUiState) {
        updateData { copy(profileInformationUiState = user.profileInformationUiState) }
    }

    private fun onGetCurrentUserFail(throwable: Throwable) {
        updateData { copy(baseUiState = this.baseUiState.copy(errorMessage = throwable.message.toString())) }
    }

    override fun onUpdateProfileImage(imageUri: Uri) {
        val resolvedUri = imageUri.toString()
        updateProfileField { copy(imageUrl = resolvedUri) }
    }

    override fun onEditButtonClicked() {
        manageUserInfoEdit(isEditable = true)
    }

    override fun onUsernameChange(newName: String) {
        updateProfileField { copy(name = newName) }
    }

    override fun onPhoneNumberChange(newNumber: String) {
        updateProfileField { copy(phoneNumber = newNumber) }
    }

    override fun onLocationChange(location: String) {
        updateProfileField { copy(location = location) }
    }

    override fun onBioChange(bio: String) {
        updateProfileField { copy(bio = bio) }
    }

    private fun updateProfileField(update: ProfileInformationUiState.() -> ProfileInformationUiState) {
        updateData {
            copy(profileInformationUiState = profileInformationUiState.update())
        }
    }

    override fun onCancelButtonClicked() {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()
//        updateData {
//            copy(profileInformationUiState = originalProfileInformation.profileInformationUiState)
//        }
    }

    override fun onSaveButtonClicked() {
        val lastUserInfo = _state.value.data.profileInformationUiState
        tryToExecute(
            call = {
                validateUserInfoUseCase(
                    name = lastUserInfo.name,
                    phoneNumber = lastUserInfo.phoneNumber,
                    location = lastUserInfo.location
                )
            },
            onSuccess = { onUpdateUserInfoSuccess() },
            onError = ::onUpdateUserInfoFail,
        )
    }

    override fun onDarkMoodChange(isDarkMood: Boolean) {
        updateData {
            copy(profileSettingsUiState = profileSettingsUiState.copy(isDarkTheme = isDarkMood))
        }
        viewModelScope.launch(Dispatchers.IO) { customizeProfileSettings.updateDarkTheme(isDarkMood) }
    }

    override fun onLogoutClicked() {
        sendUiEffect(ProfileEffect.NavigateToLoginScreen)
        //todo: clear stored user data
    }

    override fun onResetPasswordClicked() {
        sendUiEffect(ProfileEffect.NavigateToResetPassword)
    }

    override fun onChangeLanguageClicked() {
        //todo send show language dialog effect
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

    private fun onUpdateUserInfoSuccess() {
        manageUserInfoEdit(isEditable = false)
        makeErrorMessagesEmpty()

    }

    private fun onUpdateUserInfoFail(throwable: Throwable) {
        when (throwable) {
            is InvalidUsernameException -> {
                updateFieldError(userNameError = stringsResource.invalidUsername)
            }

            is InvalidPhoneNumberException -> {
                updateFieldError(phoneNumberError = stringsResource.invalidPhoneNumber)
            }

            is InvalidLocationException -> {
                updateFieldError(locationError = stringsResource.invalidLocation)
            }

            else -> updateFieldError()
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
}