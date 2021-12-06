package by.happygnom.plato.ui.screens.user

import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.theme.Teal1
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserScreen(
    viewModel: UserViewModel,
    navController: NavController,
    onSignOut: () -> Unit
) {
    Column {
        Text(
            text = "User",
            style = MaterialTheme.typography.h1.copy(Teal1),
            modifier = Modifier.padding(16.dp)
        )

        TealStrokeButton(onClick = { onSignOut() }, text = "Sign out")
    }
}
