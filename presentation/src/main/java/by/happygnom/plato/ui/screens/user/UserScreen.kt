package by.happygnom.plato.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.theme.Teal1

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
