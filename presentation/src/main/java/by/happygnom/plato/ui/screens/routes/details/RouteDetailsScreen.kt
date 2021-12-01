package by.happygnom.plato.ui.screens.routes.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun RouteDetailsScreen(
    listViewModel: RouteDetailsViewModel,
    navController: NavController,
    routeId: String
) {
    Text(
        text = "Route $routeId details",
        style = MaterialTheme.typography.h1.copy(Teal1),
        modifier = Modifier.padding(16.dp)
    )
}
