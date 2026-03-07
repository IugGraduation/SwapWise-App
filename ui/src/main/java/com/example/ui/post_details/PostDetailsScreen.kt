package com.example.ui.post_details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.domain.model.PostItem
import com.example.domain.post.GetFakePostDetailsUseCase
import com.example.ui.R
import com.example.ui.base.MyUiState
import com.example.ui.components.AsyncContent
import com.example.ui.components.atoms.DetailsScreenBody
import com.example.ui.components.atoms.HorizontalSpacer
import com.example.ui.components.atoms.SwapWiseFilledButton
import com.example.ui.components.atoms.VerticalSpacer
import com.example.ui.components.molecules.DetailsScreenUserHeader
import com.example.ui.components.molecules.ProductImage
import com.example.ui.components.molecules.TitledChipsList
import com.example.ui.components.templates.TitledScreenTemplate
import com.example.ui.edit_post.navigateToEditPost
import com.example.ui.models.AsyncState
import com.example.ui.models.PostItemUiState
import com.example.ui.post_details.composable.PhoneRow
import com.example.ui.profile.composable.EditIconButton
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.GraduationProjectTheme
import com.example.ui.theme.IconSizeSmall
import com.example.ui.theme.RadiusLarge
import com.example.ui.theme.Spacing16
import com.example.ui.theme.Spacing24
import com.example.ui.theme.Spacing4
import com.example.ui.theme.Spacing8
import com.example.ui.theme.Spacing80
import com.example.ui.theme.TextStyles
import com.example.ui.theme.color
import androidx.core.net.toUri

@Composable
fun PostDetailsScreen(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PostDetailsEffects.NavigateToEditPost -> {
                    navController.navigateToEditPost(effect.postId)
                }

                is PostDetailsEffects.NavigateUp -> navController.navigateUp()

                PostDetailsEffects.NavigateToPhone -> {
                    state.data.postItem.data?.let { post ->
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:${post.user.phone}".toUri()
                        }
                        context.startActivity(intent)
                    }
                }

                PostDetailsEffects.NavigateToWhatsapp -> {
                    state.data.postItem.data?.let { post ->
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = "https://wa.me/${post.user.phone}".toUri()
                        }
                        context.startActivity(intent)
                    }
                }

                PostDetailsEffects.NavigateToMessages -> {
                    state.data.postItem.data?.let { post ->
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = "sms:${post.user.phone}".toUri()
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
    PostDetailsContent(
        state = state,
        postDetailsInteractions = viewModel,
    )
}

@Composable
fun PostDetailsContent(
    state: MyUiState<PostItemUiState>,
    postDetailsInteractions: PostDetailsInteractions,
) {
    TitledScreenTemplate(
        title = stringResource(R.string.post_details),
        onClickGoBack = postDetailsInteractions::navigateUp,
        onRetry = postDetailsInteractions::onClickRetry,
        floatingActionButton = {
            AnimatedVisibility(state.data.postItem is AsyncState.Success && !state.data.showEditPostButton) {
                SwapWiseFilledButton(
                    onClick = postDetailsInteractions::onClickWhatsappButton,
                    text = stringResource(R.string.contact_on_whatsapp),
                    modifier = Modifier.padding(horizontal = Spacing16)
                )
            }
        },
        actions = {
            AnimatedVisibility(state.data.showEditPostButton) {
                state.data.postItem.data?.let { post ->
                    EditIconButton { postDetailsInteractions.navigateToEditPost(post.id) }
                }
            }
        },
    ) {
        AsyncContent(
            state = state.data.postItem,
            onRetry = postDetailsInteractions::onClickRetry
        ) { postItem ->
            LazyColumn {
                item {
                    ProductImage(postItem.imageUrl)
                    VerticalSpacer(Spacing16)
                    DetailsScreenUserHeader(
                        user = postItem.user,
                        date = postItem.date
                    )
                    VerticalSpacer(Spacing24)
                    StatusRow(
                        rate = postItem.rate,
                        isOpen = postItem.isOpen
                    )
                    VerticalSpacer(Spacing24)
                    DetailsScreenBody(postItem.name, postItem.details)
                    VerticalSpacer(Spacing24)
                    LocationRow(location = postItem.locationItem.name)
                    VerticalSpacer(Spacing24)
                    TitledChipsList(
                        title = stringResource(R.string.categories),
                        state = state.data.categories,
                    )
                    VerticalSpacer(Spacing24)
                    TitledChipsList(
                        title = stringResource(R.string.favorite_categories),
                        state = state.data.favoriteCategories,
                    )
                    if (postItem.user.phone.isNotBlank() && !state.data.showEditPostButton) {
                        VerticalSpacer(Spacing24)
                        Text(
                            text = stringResource(R.string.phone),
                            style = TextStyles.headingMedium,
                            color = MaterialTheme.color.textPrimary,
                            modifier = Modifier.padding(horizontal = Spacing16)
                        )
                        VerticalSpacer(Spacing8)
                        PhoneRow(
                            phone = postItem.user.phone,
                            onClickPhoneButton = postDetailsInteractions::onClickPhoneButton,
                            onClickWhatsappButton = postDetailsInteractions::onClickWhatsappButton,
                            onClickMessageButton = postDetailsInteractions::onClickMessageButton
                        )
                    }
                    VerticalSpacer(Spacing80) //space for floating button at the bottom
                }
            }
        }
    }
}

@Composable
fun LocationRow(location: String) {
    Column(modifier = Modifier.padding(horizontal = Spacing16)) {
        Text(
            text = stringResource(id = R.string.location),
            style = TextStyles.headingMedium,
            color = MaterialTheme.color.textPrimary
        )
        VerticalSpacer(Spacing8)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing4)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = location,
                tint = MaterialTheme.color.textTertiary,
                modifier = Modifier.size(IconSizeSmall)
            )
            Text(
                text = location,
                style = TextStyles.bodyLarge,
                color = MaterialTheme.color.textTertiary
            )
        }
    }
}

@Composable
fun StatusRow(rate: Float, isOpen: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostDetailsStatusItem(title = stringResource(R.string.rate), value = rate.toString())
        HorizontalSpacer(Spacing24)
        Spacer(
            modifier = Modifier
                .size(width = 1.5.dp, height = 20.dp)
                .background(color = BlackFourth, shape = RoundedCornerShape(RadiusLarge))
        )
        HorizontalSpacer(Spacing24)
        val state = if (isOpen) stringResource(R.string.open) else stringResource(R.string.closed)
        PostDetailsStatusItem(title = stringResource(R.string.state), value = state)
    }
}

@Composable
fun PostDetailsStatusItem(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = TextStyles.headingSmall,
            color = MaterialTheme.color.textPrimary
        )
        VerticalSpacer(Spacing4)
        Text(
            title,
            style = TextStyles.captionLarge,
            color = MaterialTheme.color.textTertiary
        )
    }
}


@Preview(
    showBackground = true, showSystemUi = false,
    device = "spec:width=1080px,height=3040px,dpi=440", apiLevel = 34,
)
@Composable
fun PreviewPostDetailsContent() {
    GraduationProjectTheme {
        PostDetailsContent(
            state = MyUiState(PostItemUiState(postItem = AsyncState.Success(GetFakePostDetailsUseCase()()))),
            postDetailsInteractions = object : PostDetailsInteractions {
                override fun navigateToEditPost(postId: String) {}
                override fun navigateUp() {}
                override fun onClickPhoneButton() {}
                override fun onClickWhatsappButton() {}
                override fun onClickMessageButton() {}
                override fun onClickRetry() {}
            },
        )
    }
}
