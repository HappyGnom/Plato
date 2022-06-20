package by.happygnom.plato.ui.screens.news.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.navigation.ArgNames
import by.happygnom.plato.navigation.NewsScreen
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.LoadingIndicator
import by.happygnom.plato.ui.theme.CardShape
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey2
import by.happygnom.plato.util.toFormattedDateTimeString
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsDetailsScreen(
    viewModel: NewsDetailsViewModel,
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
                text = stringResource(id = R.string.news),
                startIconId = R.drawable.ic_back,
                endIconId = if (AuthenticatedUser.isAdmin) R.drawable.ic_edit else null,
                onStartIconClick = { navController.popBackStack() },
                onEndIconClick = { navController.navigate(NewsScreen.Editor.createRoute(viewModel.news.value?.id)) }
            )
        }
    ) {
        NewsDetailsScreenContent(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun NewsDetailsScreenContent(
    viewModel: NewsDetailsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val news by viewModel.news.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { viewModel.loadNews(true) },
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        if (news == null)
            Text(
                text = stringResource(id = R.string.failed_to_load_route),
                style = MaterialTheme.typography.body1,
                modifier = modifier.padding(16.dp)
            )
        else
            Column(
                modifier = modifier.verticalScroll(rememberScrollState())
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 88.dp)
                ) {
                    Text(
                        text = news!!.header,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = news!!.publishDateTime.toFormattedDateTimeString(),
                        style = MaterialTheme.typography.caption.copy(Grey2),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = rememberImagePainter(
                            data = news!!.pictureUrl,
                            builder = {
                                placeholder(R.drawable.placeholder_plato)
                                error(R.drawable.placeholder_plato)
                                fallback(R.drawable.placeholder_plato)
                                size(OriginalSize)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CardShape),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = news!!.text,
                        style = MaterialTheme.typography.body1.copy(Grey1),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
    }
}

