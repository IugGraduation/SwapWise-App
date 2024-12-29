package com.example.ui.profile.composable

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.ui.R
import com.example.ui.profile.ProfileUiState
import com.example.ui.theme.Border1
import com.example.ui.theme.IconSizeLarge
import com.example.ui.theme.IconSizeMedium
import com.example.ui.theme.ImageSize120
import com.example.ui.theme.Primary
import com.example.ui.theme.Spacing4
import com.example.ui.theme.WhitePrimary

@Composable
fun ProfileImage(state: ProfileUiState, onImageChangeClick: (newUri: Uri) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.imageUrl, ){
        Log.i("IMAGE",state.imageUrl)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ uri: Uri? ->
        uri?.let {
            onImageChangeClick(it)
        }
    }

    Box(Modifier.height(ImageSize120)) {
        Box(
            modifier = Modifier
                .size(ImageSize120)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val imageRequest = ImageRequest.Builder(context)
                .data(state.imageUrl)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .transformations(CircleCropTransformation())
                .build()
            val imageLoader =
                ImageLoader.Builder(context)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build()

            Image(
                modifier = Modifier
                    .size(ImageSize120)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(imageRequest, imageLoader),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .size(IconSizeLarge)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .border(Border1, WhitePrimary, CircleShape),
            colors = IconButtonDefaults.iconButtonColors(Primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                modifier = Modifier
                    .size(IconSizeMedium)
                    .padding(Spacing4),
                tint = Primary
            )
        }
    }
}

@Preview
@Composable
private fun ProfileImagePreview() {
    ProfileImage(ProfileUiState() ,{})
}
