package by.happygnom.plato.ui.screens.routes.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.Comment
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.LoadingIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CommentsScreen(
    viewModel: CommentsViewModel,
    navController: NavController,
) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun CommentsScreenContent(
    viewModel: CommentsViewModel,
    modifier: Modifier = Modifier,
) {
    val comments by viewModel.comments.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { viewModel.loadComments() },
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier,
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 48.dp)
        ) {
            if (comments.isNullOrEmpty())
                item {
                    Text(
                        text = stringResource(id = R.string.no_comments),
                        style = MaterialTheme.typography.body1
                    )
                }
            else
                items(comments) { comment ->
                    Comment(
                        comment = comment,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
        }
    }
}
