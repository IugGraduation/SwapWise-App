package com.example.ui.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.molecules.PasswordTextField
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles.hint
import com.example.ui.theme.color
import com.example.ui.util.CollectUiEffect
import com.example.ui.util.empty

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectUiEffect(viewModel.effect) { effect ->
        when (effect) {
            ResetPasswordEffect.PopUpToPreviousScreen -> navController.popBackStack()
        }
    }

    ResetPasswordContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun ResetPasswordContent(
    state: MyUiState<ResetPasswordUiState>,
    interaction: ResetPasswordInteraction
) {
    TitledScreenTemplate(
        title = stringResource(R.string.reset_Password),
        onClickGoBack = interaction::onPopUpToPreviousScreen,
        baseUiState = state.baseUiState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.color.background),
            verticalArrangement = Arrangement.spacedBy(Spacing16)
        ) {
            LabeledPasswordField(
                label = stringResource(R.string.current_password),
                value = state.data.currentPassword,
                placeholder = stringResource(R.string.current_password),
                onValueChange = interaction::onCurrentPasswordChange,
                isPasswordVisible = state.data.resetPasswordVisibilityUiState.isCurrentPasswordVisible,
                errorMessage = state.data.resetPasswordErrorUiState.currentPasswordErrorMessage,
                onVisibilityToggle = interaction::toggleCurrentPasswordVisibility,
            )

            LabeledPasswordField(
                label = stringResource(R.string.new_password),
                value = state.data.newPassword,
                placeholder = stringResource(R.string.new_password),
                onValueChange = interaction::onNewPasswordChange,
                isPasswordVisible = state.data.resetPasswordVisibilityUiState.isNewPasswordVisible,
                errorMessage = state.data.resetPasswordErrorUiState.newPasswordErrorMessage,
                onVisibilityToggle = interaction::toggleNewPasswordVisibility,
            )

            LabeledPasswordField(
                label = stringResource(R.string.confirm_new_password),
                value = state.data.confirmNewPassword,
                placeholder = stringResource(R.string.confirm_new_password),
                onValueChange = interaction::onConfirmNewPasswordChange,
                isPasswordVisible = state.data.resetPasswordVisibilityUiState.isConfirmNewPasswordVisible,
                errorMessage = state.data.resetPasswordErrorUiState.confirmNewPasswordErrorMessage,
                onVisibilityToggle = interaction::toggleConfirmNewPasswordVisibility,
            )

            Spacer(modifier = Modifier.weight(6f))

            SwapWiseFilledButton(
                modifier = Modifier.padding(Spacing16),
                onClick = {},
                text = stringResource(R.string.reset)
            )

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Composable
private fun LabeledPasswordField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    errorMessage: String? = String.empty(),
    onVisibilityToggle: () -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = Spacing16)
        .background(color = MaterialTheme.color.background),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        Text(text = label, style = hint, color = MaterialTheme.color.textPrimary)
        PasswordTextField(
            value = value,
            placeholder = placeholder,
            onValueChange = onValueChange,
            isPasswordVisible = isPasswordVisible,
            onVisibilityToggle = onVisibilityToggle,
            errorMessage = errorMessage
        )
    }
}

