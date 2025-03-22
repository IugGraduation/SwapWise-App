package com.example.ui.profile

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.atoms.MultiChoiceDialog
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseOutlineButton
import com.example.ui.components.atoms.SwapWiseSwitch
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.TransparentStatusBar
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.atoms.updateResources
import com.example.ui.components.molecules.PostCard
import com.example.ui.components.templates.BottomBarTemplate
import com.example.ui.login.navigateToLogin
import com.example.ui.models.BottomBarUiState
import com.example.ui.post_details.navigateToPostDetails
import com.example.ui.profile.composable.EditIconButton
import com.example.ui.profile.composable.GradientCircleBackground
import com.example.ui.profile.composable.ProfileDialog
import com.example.ui.profile.composable.ProfileHorizontalPager
import com.example.ui.profile.composable.ProfileImage
import com.example.ui.profile.composable.ProfileToggle
import com.example.ui.profile.composable.SettingsRow
import com.example.ui.profile.composable.UserActivitiesBar
import com.example.ui.profile.composable.VerticalBoldAndLightText
import com.example.ui.reset_password.navigateToResetPassword
import com.example.ui.shared.BottomNavigationViewModel
import com.example.ui.theme.GradientCircleBackgroundSize
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing40
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles.headingExtraLarge
import com.example.ui.theme.color
import com.example.ui.util.CollectUiEffect
import java.util.Locale

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val selectedItem by bottomNavigationViewModel.selectedItem.collectAsState()
    val bottomBarState = BottomBarUiState(
        selectedItem = selectedItem,
        onItemSelected = bottomNavigationViewModel::onItemSelected,
        navController = navController
    )

    LaunchedEffect(pagerState.currentPage) { viewModel.updatePagerNumber(pagerState.currentPage) }

    CollectUiEffect(viewModel.effect) { effect ->
        when (effect) {
            ProfileEffect.NavigateToLoginScreen -> navController.navigateToLogin {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }

            ProfileEffect.NavigateToResetPassword -> {
                navController.navigateToResetPassword()
            }

            is ProfileEffect.NavigateToPostDetails -> {
                navController.navigateToPostDetails(effect.postId)
            }
        }
    }

    ProfileContent(
        state = state,
        profileInteraction = viewModel,
        pagerState = pagerState,
        bottomBarState = bottomBarState
    )
}

@Composable
private fun ProfileContent(
    state: MyUiState<ProfileUiState>,
    profileInteraction: ProfileInteraction,
    pagerState: PagerState,
    bottomBarState: BottomBarUiState,
) {
    TransparentStatusBar()

    BottomBarTemplate(bottomBarState = bottomBarState, baseUiState = state.baseUiState) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.color.background)
        ) {
            item {
                Box {
                    GradientCircleBackground(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(GradientCircleBackgroundSize)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing24),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Spacing16, vertical = Spacing40)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Spacing24),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.profile),
                                color = MaterialTheme.color.background,
                                style = headingExtraLarge,
                            )

                            AnimatedVisibility(pagerState.currentPage == 0) {
                                EditIconButton(onClick = profileInteraction::onEditButtonClicked)
                            }
                        }

                        ProfileImage(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            state = state.data,
                            onImageChangeClick = profileInteraction::onUpdateProfileImage
                        )

                        VerticalBoldAndLightText(
                            modifier = Modifier.padding(top = Spacing16),
                            boldText = state.data.profileInformationUiState.name,
                            boldStyle = headingExtraLarge,
                            lightText = state.data.profileInformationUiState.bio
                        )

                        UserActivitiesBar(
                            postsNumber = state.data.profileInformationUiState.postsNumber,
                            offersNumber = state.data.profileInformationUiState.offersNumber,
                            exchangesNumber = state.data.profileInformationUiState.exchangesNumber
                        )

                        ProfileToggle(pagerState = pagerState)

                        ProfileHorizontalPager(
                            pagerState = pagerState,
                            informationContent = {
                                UserInformationSection(
                                    state = state.data, profileInteraction = profileInteraction,
                                )
                            },

                            postsContent = {
                                UserPostsSection(
                                    state = state.data, profileInteraction = profileInteraction,
                                )
                            },

                            settingsContent = {
                                SettingsSection(
                                    state = state.data,
                                    interaction = profileInteraction
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(state.data.profileSettingsUiState.showLogoutDialog) {
        ProfileDialog(
            title = stringResource(R.string.logout_title),
            text = stringResource(R.string.logout_content_message),
            onDismissButtonClick = { profileInteraction.onUpdateLogoutDialogState(false) },
            onConfirmButtonClick = {
                profileInteraction.onUpdateLogoutDialogState(false)
                profileInteraction.onLogoutClicked()
            }
        )
    }

    AnimatedVisibility(state.data.profileSettingsUiState.showLanguageDialog) {
        val englishLanguageCode = stringResource(R.string.english_language_code)
        val context = LocalContext.current
        val profileSettingsState = state.data.profileSettingsUiState
        MultiChoiceDialog(
            onClickDone = { language ->
                profileInteraction.updateLanguageDialogState(false)
                val languageCode = profileSettingsState.languageMap[language] ?: englishLanguageCode
                profileInteraction.onUpdateLanguage(languageCode)
                updateResources(context = context, localeLanguage = Locale(languageCode))
            },
            onDismissRequest = { profileInteraction.updateLanguageDialogState(false) },
            choices = profileSettingsState.languageMap.keys.toList(),
            oldSelectedChoice = if (profileSettingsState.lastAppLanguage == profileSettingsState.languageMap[LocalLanguage.Arabic.name])
                LocalLanguage.Arabic.name
            else
                LocalLanguage.English.name

        )
    }

}

@Composable
private fun UserInformationSection(
    state: ProfileUiState,
    profileInteraction: ProfileInteraction,
    modifier: Modifier = Modifier
) {
    val isUserInfoEditable = state.profileInformationUiState.isUserInfoEditable

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {

        SwapWiseTextField(
            value = state.profileInformationUiState.name,
            onValueChange = profileInteraction::onUsernameChange,
            placeholder = stringResource(R.string.full_name),
            errorMessage = state.profileError.userNameErrorMessage,
            isEditable = isUserInfoEditable,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = state.profileInformationUiState.name,
                    tint = MaterialTheme.color.textTertiary
                )
            }
        )

        SwapWiseTextField(
            value = state.profileInformationUiState.phoneNumber,
            onValueChange = profileInteraction::onPhoneNumberChange,
            isEditable = isUserInfoEditable,
            placeholder = stringResource(R.string.phone_number),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            errorMessage = state.profileError.phoneNumberErrorMessage,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = stringResource(R.string.phone_number),
                    tint = MaterialTheme.color.textTertiary
                )
            }
        )

        SwapWiseTextField(
            value = state.profileInformationUiState.location,
            onValueChange = profileInteraction::onLocationChange,
            isEditable = isUserInfoEditable,
            placeholder = stringResource(R.string.best_barter_spot),
            errorMessage = state.profileError.locationErrorMessage,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = state.profileInformationUiState.location,
                    tint = MaterialTheme.color.textTertiary
                )
            }
        )

        SwapWiseTextField(
            value = state.profileInformationUiState.bio,
            onValueChange = profileInteraction::onBioChange,
            isEditable = isUserInfoEditable,
            placeholder = stringResource(R.string.bio),
            errorMessage = state.profileError.bioErrorMessage,
            isMultiline = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_bio),
                    contentDescription = state.profileInformationUiState.bio,
                    tint = MaterialTheme.color.textTertiary
                )
            }
        )

        VerticalSpacer(height = Spacing16)

        AnimatedVisibility(state.profileInformationUiState.isUserInfoEditable) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing8)
            ) {
                VerticalSpacer(Spacing8)
                SwapWiseFilledButton(
                    onClick = profileInteraction::onSaveButtonClicked,
                    text = stringResource(R.string.save)
                )

                SwapWiseOutlineButton(
                    text = stringResource(R.string.cancel),
                    onClick = profileInteraction::onCancelButtonClicked
                )
            }
        }

    }
}

@Composable
private fun UserPostsSection(
    state: ProfileUiState,
    profileInteraction: ProfileInteraction,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        items(items = state.userPosts) { postItem ->
            PostCard(
                username = postItem.username,
                userImage = rememberAsyncImagePainter(postItem.userImageLink),
                isOpen = postItem.isThePostOpen,
                title = postItem.postTitle,
                isPostCard = false,
                details = postItem.postDescription,
                offersNumber = postItem.offersNumber.toString(),
                postImage = rememberAsyncImagePainter(postItem.postImageLink),
                onCardClick = { profileInteraction.navigateToPostDetails(postItem.id) }
            )

        }
    }
}

@Composable
private fun SettingsSection(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
    interaction: ProfileInteraction,
) {
    Column(
        modifier = modifier.padding(Spacing8),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        SettingsRow(
            title = stringResource(R.string.dark_theme),
            onRowClick = { },
            isClickEnable = false,
            leadingIconResource = painterResource(R.drawable.ic_moon),
            trailingIcon = {
                SwapWiseSwitch(
                    state = state.profileSettingsUiState.isDarkTheme,
                    onCheckedChange = { interaction.onDarkMoodChange(it) }
                )
            }
        )
        SettingsRow(
            title = stringResource(R.string.reset_Password),
            onRowClick = interaction::onResetPasswordClicked,
            leadingIconResource = painterResource(R.drawable.ic_reset_password),
        )

        SettingsRow(
            title = stringResource(R.string.language),
            onRowClick = { interaction.updateLanguageDialogState(true) },
            leadingIconResource = painterResource(R.drawable.ic_language),
        )

        SettingsRow(
            title = stringResource(R.string.log_out),
            contentColor = MaterialTheme.color.danger,
            onRowClick = { interaction.onUpdateLogoutDialogState(showDialog = true) },
            leadingIconResource = painterResource(R.drawable.ic_logout),
        )

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })

        ProfileContent(
            state = MyUiState(
                ProfileUiState()
            ),
            profileInteraction = object : ProfileInteraction {
                override fun onUpdateProfileImage(imageUri: Uri) {}
                override fun onEditButtonClicked() {}
                override fun onUsernameChange(newName: String) {}
                override fun onPhoneNumberChange(newNumber: String) {}
                override fun onLocationChange(location: String) {}
                override fun onBioChange(bio: String) {}
                override fun onCancelButtonClicked() {}
                override fun onSaveButtonClicked() {}
                override fun onDarkMoodChange(isDarkMood: Boolean) {}
                override fun onLogoutClicked() {}
                override fun onResetPasswordClicked() {}
                override fun onUpdateLogoutDialogState(showDialog: Boolean) {}
                override fun updateLanguageDialogState(showDialog: Boolean) {}
                override fun onUpdateLanguage(language: String) {}
                override fun navigateToPostDetails(postId: String) {}
            },
            pagerState = pagerState,
            bottomBarState = BottomBarUiState(),
        )
    }
}