package by.happygnom.plato.ui.screens.news.news_editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun NewsEditorScreen(
    viewModel: NewsEditorViewModel,
    navController: NavController
) {
    Text(
        text = "News",
        style = MaterialTheme.typography.h1.copy(Teal1),
        modifier = Modifier.padding(16.dp)
    )
}
