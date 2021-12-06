package by.happygnom.plato.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.button.GreyStrokeButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.theme.Grey1
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun Permission(
    permission: String,
    onRequestDismissed: () -> Unit,
    rationale: String = stringResource(id = R.string.permission_required_info),
    notAvailableRationale: String = stringResource(id = R.string.permission_not_available),
    content: @Composable () -> Unit = { }
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission)

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            RationaleDialog(
                text = rationale,
                onRequestDismissed = onRequestDismissed,
                onRequestPermission = { permissionState.launchPermissionRequest() }
            )
        },
        permissionNotAvailableContent = {
            PermissionNotAvailableDialog(
                text = notAvailableRationale,
                onRequestDismissed = onRequestDismissed,
                onRequestSettings = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }
            )
        },
        content = content
    )
}

@Composable
private fun RationaleDialog(
    text: String,
    onRequestDismissed: () -> Unit,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onRequestDismissed,
        title = {
            Text(
                text = stringResource(id = R.string.permission_request),
                style = MaterialTheme.typography.body1
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.body1.copy(Grey1)
            )
        },
        dismissButton = {
            GreyStrokeButton(
                text = stringResource(id = R.string.cancelation),
                onClick = onRequestDismissed,
                modifier = Modifier.padding(bottom = 14.dp)
            )
        },
        confirmButton = {
            TealFilledButton(
                text = stringResource(id = R.string.ok),
                onClick = onRequestPermission,
                modifier = Modifier.padding(bottom = 14.dp, end = 8.dp)
            )
        }
    )
}

@Composable
private fun PermissionNotAvailableDialog(
    text: String,
    onRequestDismissed: () -> Unit,
    onRequestSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onRequestDismissed,
        title = {
            Text(
                text = stringResource(id = R.string.permission_request),
                style = MaterialTheme.typography.body1
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.body1
            )
        },
        dismissButton = {
            GreyStrokeButton(
                text = stringResource(id = R.string.cancelation),
                onClick = onRequestDismissed,
                modifier = Modifier.padding(bottom = 14.dp)
            )
        },
        confirmButton = {
            TealFilledButton(
                text = stringResource(id = R.string.open_settings),
                onClick = onRequestSettings,
                modifier = Modifier.padding(bottom = 14.dp, end = 8.dp)
            )
        }
    )
}
