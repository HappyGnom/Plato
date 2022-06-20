package by.happygnom.plato.ui.screens.news.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.domain.model.News
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.navigation.ArgNames
import by.happygnom.plato.navigation.NewsScreen
import by.happygnom.plato.ui.elements.Card
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.LoadingIndicator
import by.happygnom.plato.ui.elements.button.AddFloatingActionButton
import by.happygnom.plato.ui.theme.*
import by.happygnom.plato.util.toFormattedDateTimeString
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    navController: NavController
) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(ArgNames.SHOULD_UPDATE)
        ?.observe(LocalLifecycleOwner.current) {
            viewModel.loadNews(true)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>(ArgNames.SHOULD_UPDATE)
        }

    Scaffold(
        topBar = {
            DefaultToolbar(
                text = stringResource(id = R.string.latest_news)
            )
        },
        floatingActionButton = {
            if (AuthenticatedUser.isAdmin)
                AddFloatingActionButton(
                    onClick = {
                        navController.navigate(NewsScreen.Editor.createRoute(null))
                    }
                )
        }
    ) {
        NewsListScreenContent(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun NewsListScreenContent(
    viewModel: NewsListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val latestNews by viewModel.news.observeAsState(listOf())
    val isLoading by viewModel.isLoading.observeAsState(false)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { viewModel.loadNews(forceUpdate = true) },
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 88.dp),
            modifier = modifier
        ) {
            if (latestNews.isEmpty())
                item {
                    Text(
                        text = stringResource(id = R.string.no_news),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            else
                items(latestNews) { news ->
                    NewsCard(
                        news = news,
                        onClick = {
                            navController.navigate(
                                NewsScreen.NewsDetails.createRoute(news.id)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
        }
    }
}

@Composable
fun NewsCard(
    news: News,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val blackAndWhiteMatrix = ColorMatrix().apply { setToSaturation(0f) }

    val cardColor = if (news.isPublished) White else Grey5
    val imageFilter = if (news.isPublished)
        null
    else
        ColorFilter.colorMatrix(blackAndWhiteMatrix)

    Card(
        modifier = modifier,
        color = cardColor,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = news.pictureUrl,
                        builder = {
                            placeholder(R.drawable.placeholder_plato)
                            error(R.drawable.placeholder_plato)
                            fallback(R.drawable.placeholder_plato)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    colorFilter = imageFilter,
                    modifier = Modifier.fillMaxWidth()
                )

                if(!news.isPublished)
                Text(
                    text = stringResource(id = R.string.unpublished),
                    style = MaterialTheme.typography.h1.copy(color = Pink1),
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = news.header,
                    style = MaterialTheme.typography.h2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

                Text(
                    text = news.publishDateTime.toFormattedDateTimeString(),
                    style = MaterialTheme.typography.body2.copy(Grey1),
                    maxLines = 1
                )

                Text(
                    text = news.text,
                    style = MaterialTheme.typography.body2.copy(Grey2),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
    }
}
