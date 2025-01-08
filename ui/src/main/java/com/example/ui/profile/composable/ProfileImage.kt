package com.example.ui.profile.composable

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.ui.R
import com.example.ui.profile.ProfileUiState
import com.example.ui.theme.BorderWidth2
import com.example.ui.theme.IconButtonSize32
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.ImageSize120
import com.example.ui.theme.Primary
import com.example.ui.theme.WhitePrimary
import com.example.ui.theme.ZeroDp

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
    onImageChangeClick: (newUri: Uri) -> Unit
) {
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let(onImageChangeClick)
    }

    Box(modifier = modifier) {
        ProfileImageContent(
            imageUrl = state.profileInformationUiState.imageUrl,
            context = context,
            modifier = Modifier
                .size(ImageSize120)
                .border(BorderWidth2, WhitePrimary, CircleShape)
                .clip(CircleShape)
        )

        ImageChangeButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier
                .size(IconButtonSize32)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun ProfileImageContent(
    imageUrl: String?,
    context: Context,
    modifier: Modifier = Modifier
) {
    @DrawableRes
    val placeholderRes = R.drawable.placeholder_profile

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl?.ifEmpty { null })
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .transformations(CircleCropTransformation())
        .placeholder(placeholderRes)
        .error(placeholderRes)
        .build()

    val imageLoader = ImageLoader.Builder(context)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    Image(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        painter = rememberAsyncImagePainter(imageRequest, imageLoader),
        contentDescription = null ,
    )
}

@Composable
private fun ImageChangeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        contentPadding = PaddingValues(ZeroDp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = null,
            modifier = Modifier.size(IconSizeMedium),
            tint = WhitePrimary
        )
    }
}

