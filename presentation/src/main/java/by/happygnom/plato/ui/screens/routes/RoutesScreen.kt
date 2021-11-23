package by.happygnom.plato.ui.screens.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.screens.settings.SettingsViewModel
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun RoutesScreen(
    viewModel: RoutesViewModel,
    navController: NavController
) {
    Text(
        text = "Routes",
        style = MaterialTheme.typography.h1.copy(Teal1),
        modifier = Modifier.padding(16.dp)
    )
}
