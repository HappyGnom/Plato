package by.happygnom.plato.ui.screens.routes.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.Comment
import by.happygnom.plato.ui.elements.DefaultToolbar

@Composable
fun CommentsScreen(
    viewModel: CommentsViewModel,
    navController: NavController,
    routeId: Long
) {
//        viewModel.loadRouteData(routeId)
    Scaffold(
        topBar = {
            DefaultToolbar(
                text = stringResource(id = R.string.comments),
                startIconId = R.drawable.ic_back,
                onStartIconClick = { navController.popBackStack() }
            )
        }
    ) {
        CommentsScreenContent(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun CommentsScreenContent(
    viewModel: CommentsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 48.dp)
    ) {
        repeat(5) {
            item {
                Comment(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
