package by.happygnom.plato.ui.screens.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    Text(
        text = "Profile",
        style = MaterialTheme.typography.h1.copy(Teal1),
        modifier = Modifier.padding(16.dp)
    )
}
