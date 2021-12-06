package by.happygnom.plato.util

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import by.happygnom.plato.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraTakePicture(onImageUri: (Uri?) -> Unit) {
    val context = LocalContext.current

    val photoFile = File.createTempFile(
        "IMG_", ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success)
                onImageUri(uri)
            else
                onImageUri(null)
        }
    )

    @Composable
    fun LaunchCamera() {
        SideEffect {
            launcher.launch(uri)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Permission(
            permission = Manifest.permission.CAMERA,
            onRequestDismissed = { onImageUri(null) },
            rationale = stringResource(id = R.string.permission_camera_info),
        ) {
            LaunchCamera()
        }
    } else {
        LaunchCamera()
    }
}
