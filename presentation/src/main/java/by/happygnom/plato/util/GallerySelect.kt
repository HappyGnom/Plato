package by.happygnom.plato.util

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.stringResource
import by.happygnom.plato.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GallerySelect(onImageUri: (Uri?) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Permission(
            permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
            onRequestDismissed = { onImageUri(null) },
            rationale = stringResource(id = R.string.permission_media_access_info),
        ) {
            LaunchGallery()
        }
    } else {
        LaunchGallery()
    }
}

