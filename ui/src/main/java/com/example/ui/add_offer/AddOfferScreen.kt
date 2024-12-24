package com.example.ui.add_offer

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.io.File

@Composable
fun AddOfferScreen(navController: NavController, viewModel: AddOfferViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val pickImageFromGallery = pickImageFromGallery { viewModel.onSelectedImageChange(it) }
//    val captureImageFromCamera =
//        captureImageFromCameraWithPermissions { viewModel.onSelectedImageChange(it) }

    AddOfferContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onPlaceChange = viewModel::onPlaceChange,
        onDetailsChange = viewModel::onDetailsChange,
        onClickAddOffer = viewModel::onClickAddOffer,
        onClickAddImage = pickImageFromGallery,
//        onClickAddImage = captureImageFromCameraWithPermissions { viewModel.onSelectedImageChange(it) },
        onClickGoBack = { navController.navigateUp() },
    )
}


@Composable
fun pickImageFromGallery(onImagePicked: (Uri) -> Unit): () -> Unit {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImagePicked(it) }
        }

    return { galleryLauncher.launch("image/*") }
}


@Composable
fun captureImageFromCameraWithPermissions(onImageCaptured: (Uri) -> Unit): () -> Unit {
    return checkPermissions(
        permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
        onAllPermissionsGranted = {
            captureImageFromCamera { onImageCaptured(it) }
        },
        onPermissionsDenied = {
            requestPermissions(captureImageFromCamera { onImageCaptured(it) })
        }
    )
}

@Composable
fun checkPermissions(
    permissions: List<String>,
    onAllPermissionsGranted: @Composable () -> () -> Unit,
    onPermissionsDenied: @Composable () -> () -> Unit
): () -> Unit {
    val context = LocalContext.current

    val hasAllPermissions = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    return if (hasAllPermissions) {
        onAllPermissionsGranted()
    } else {
        onPermissionsDenied()
    }
}

@Composable
fun captureImageFromCamera(onImageCaptured: (Uri) -> Unit): () -> Unit {

    val context = LocalContext.current
    var capturedUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                capturedUri?.let { uri ->
                    onImageCaptured(uri)
                }
            }
        }

    return {
        val imageFile = File(context.cacheDir, "temp_image.jpg").apply {
            createNewFile()
            deleteOnExit()
        }
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
        capturedUri = uri
        cameraLauncher.launch(uri)
    }

}

@Composable
fun requestPermissions(onPermissionGranted: () -> Unit): () -> Unit {
    val context = LocalContext.current
    var result = {}
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                result = { onPermissionGranted() }
            } else {
                Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }
    return result
}