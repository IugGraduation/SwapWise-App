package com.example.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.ui.R
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.SwapWiseOutlineButton
import com.example.ui.components.atoms.SwapWiseTextField
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.PostCard
import com.example.ui.profile.composable.EditIconButton
import com.example.ui.profile.composable.GradientCircleBackground
import com.example.ui.profile.composable.ProfileHorizontalPager
import com.example.ui.profile.composable.ProfileImage
import com.example.ui.profile.composable.ProfileToggle
import com.example.ui.profile.composable.UserActivitiesBar
import com.example.ui.profile.composable.VerticalBoldAndLightText
import com.example.ui.theme.BlackPrimary
import com.example.ui.theme.BlackTertiary
import com.example.ui.theme.GradientCircleBackgroundSize
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing40
import com.example.ui.theme.Spacing8
import com.example.ui.theme.TextStyles.headingExtraLarge
import com.example.ui.theme.WhitePrimary
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 },
    )
    ProfileContent(
        state = state,
        profileInteraction = viewModel,
        pagerState = pagerState
    )
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    profileInteraction: ProfileInteraction,
    pagerState: PagerState
    ) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = false)

    LazyColumn {
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
                            color = WhitePrimary,
                            style = headingExtraLarge,
                        )

                        EditIconButton(onClick = profileInteraction::onEditButtonClicked)
                    }

                    ProfileImage(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        state = state,
                        onImageChangeClick = profileInteraction::onUpdateProfileImage
                    )

                    VerticalBoldAndLightText(
                        modifier = Modifier.padding(top = Spacing16),
                        boldText = state.profileInformationUiState.name,
                        boldStyle = headingExtraLarge,
                        lightText = state.profileInformationUiState.bio
                    )

                    UserActivitiesBar(
                        postsNumber = state.profileInformationUiState.postsNumber,
                        offersNumber = state.profileInformationUiState.offersNumber,
                        exchangesNumber = state.profileInformationUiState.exchangesNumber
                    )

                    ProfileToggle(pagerState = pagerState)

                    ProfileHorizontalPager(
                        pagerState = pagerState,
                        informationContent = {
                            UserInformationSection(
                                state = state,
                                profileInteraction = profileInteraction
                            )
                        },
                        postsContent = { UserPostsSection(state = state) },
                        settingsContent = { SettingsSection() }
                    )
                }
            }
        }
    }
    }

@Composable
private fun UserInformationSection(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
    profileInteraction: ProfileInteraction
) {
    val isUserInfoEditable = state.profileInformationUiState.isUserInfoEditable

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {

        SwapWiseTextField(
            value = state.profileInformationUiState.name,
            onValueChange = profileInteraction::onUsernameChange,
            placeholder = stringResource(R.string.phone_number),
            errorMessage = state.profileError.userNameErrorMessage,
            isEditable = isUserInfoEditable,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = state.profileInformationUiState.name,
                    tint = BlackTertiary
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
                        tint = BlackTertiary
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
                    tint = BlackTertiary
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
                    tint = BlackTertiary
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
                    title = stringResource(R.string.cancel),
                    onClick = profileInteraction::onCancelButtonClicked
                )
            }
        }

    }
}

@Composable
private fun UserPostsSection(modifier: Modifier = Modifier, state: ProfileUiState) {
    LazyColumn(
        modifier = modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing8)
    ) {
        items(items = state.userPosts){ postItem ->
                PostCard(
                    username = postItem.username,
                    userImage = rememberAsyncImagePainter(postItem.userImageLink),
                    isOpen = postItem.isThePostOpen,
                    title = postItem.postTitle,
                    isPostCard = false,
                    details = postItem.postDescription,
                    offersNumber = postItem.offersNumber.toString(),
                    postImage = rememberAsyncImagePainter(postItem.postImageLink),
                    onCardClick = {}
                )

        }
    }
}

@Composable
private fun SettingsSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(500.dp)
        .background(color = BlackPrimary))
}


