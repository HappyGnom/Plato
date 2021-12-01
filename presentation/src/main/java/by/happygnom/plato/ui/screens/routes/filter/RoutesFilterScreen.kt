package by.happygnom.plato.ui.screens.routes.filter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun RoutesFilterScreen(
    listViewModel: RoutesFilterViewModel,
    navController: NavController
) {
    Text(
        text = "Routes filter",
        style = MaterialTheme.typography.h1.copy(Teal1),
        modifier = Modifier.padding(16.dp)
    )
}
