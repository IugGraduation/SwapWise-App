package com.example.ui.components.molecules

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.ui.R
import com.example.ui.theme.BlackFourth
import com.example.ui.theme.RadiusLarge

@Composable
fun ProductImage(
    image: String?,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier,
    onImagePicked: ((Uri) -> Unit)? = null,
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImagePicked?.invoke(it) }
        }

    Box(modifier = Modifier.clickable(enabled = onImagePicked != null) {
        galleryLauncher.launch("image/*")
    }) {
        Image(
            painter = if (image.isNullOrEmpty()) painterResource(R.drawable.ic_add_image) else rememberAsyncImagePainter(
                image
            ),
            contentDescription = stringResource(R.string.image),
            contentScale = if (image.isNullOrEmpty()) ContentScale.Inside else contentScale,
            modifier = modifier
                .fillMaxWidth()
                .height(270.dp)
                .clip(RoundedCornerShape(bottomEnd = RadiusLarge, bottomStart = RadiusLarge))
                .background(color = BlackFourth)
        )
    }
}


@Composable
fun PickImageFromGallery(onImagePicked: (Uri) -> Unit) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImagePicked(it) }
        }

    galleryLauncher.launch("image/*")
}

