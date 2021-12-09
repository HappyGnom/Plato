package by.happygnom.plato.ui.screens.routes.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.RoutesFilter
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.Card
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.LoadingIndicator
import by.happygnom.plato.ui.elements.TagsList
import by.happygnom.plato.ui.elements.button.AddFloatingActionButton
import by.happygnom.plato.ui.elements.button.LabeledIconButton
import by.happygnom.plato.ui.navigation.ArgNames
import by.happygnom.plato.ui.navigation.RoutesScreen
import by.happygnom.plato.ui.theme.*
import by.happygnom.plato.util.toFormattedDateString
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RoutesListScreen(
    viewModel: RoutesListViewModel,
    navController: NavController,
) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(ArgNames.SHOULD_UPDATE)
        ?.observe(LocalLifecycleOwner.current) {
            viewModel.loadRoutes(true)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>(ArgNames.SHOULD_UPDATE)
        }

    val displayedRoutesNameId by viewModel.displayedRoutesNameId.observeAsState(R.string.all_set)

    Scaffold(
        topBar = {
            val routesText = stringResource(id = displayedRoutesNameId) +
                    " " +
                    stringResource(id = R.string.routes).toLowerCase(Locale.current)

            DefaultToolbar(
                text = routesText,
                endIconId = R.drawable.ic_filter,
                onEndIconClick = { navController.navigate(RoutesScreen.Filter.route) }
            )
        },
        floatingActionButton = {
            if (AuthenticatedUser.isAdmin)
                AddFloatingActionButton(
                    onClick = {
                        navController.navigate(RoutesScreen.Editor.createRoute(null))
                    }
                )
        }) {
        RoutesListScreenContent(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun RoutesListScreenContent(
    viewModel: RoutesListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val routes by viewModel.routes.observeAsState(listOf())
    val isLoading by viewModel.isLoading.observeAsState(false)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { viewModel.loadRoutes(forceUpdate = true) },
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(8.dp, 12.dp, 8.dp, 88.dp),
            modifier = modifier
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    LabeledIconButton(
                        text = stringResource(id = R.string.all_set),
                        iconPainter = painterResource(id = R.drawable.ic_path),
                        onClick = {
                            viewModel.setRoutesFilterAndFetch(
                                RoutesFilter.Default,
                                R.string.all_set
                            )
                        }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.bookmarked),
                        iconPainter = painterResource(id = R.drawable.ic_bookmark),
                        onClick = {
                            val projectedRoutesFilter = RoutesFilter.Builder()
                                .setCategory(RoutesFilter.Category.BOOKMARKED)
                                .setIncludeTakenDown(true)
                                .build()

                            viewModel.setRoutesFilterAndFetch(
                                projectedRoutesFilter,
                                R.string.bookmarked
                            )
                        }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.sent),
                        iconPainter = painterResource(id = R.drawable.ic_arm),
                        onClick = {
                            val sentRoutesFilter = RoutesFilter.Builder()
                                .setCategory(RoutesFilter.Category.SENT)
                                .setIncludeTakenDown(true)
                                .build()

                            viewModel.setRoutesFilterAndFetch(
                                sentRoutesFilter,
                                R.string.sent
                            )
                        }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.liked),
                        iconPainter = painterResource(id = R.drawable.ic_like),
                        onClick = {
                            val likedRoutesFilter = RoutesFilter.Builder()
                                .setCategory(RoutesFilter.Category.LIKED)
                                .setIncludeTakenDown(true)
                                .build()

                            viewModel.setRoutesFilterAndFetch(
                                likedRoutesFilter,
                                R.string.liked
                            )
                        }
                    )
                }
            }

            if (routes.isNullOrEmpty())
                item {
                    Text(
                        text = stringResource(id = R.string.no_routes),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            else
                items(routes) { route ->
                    RouteCard(
                        route = route,
                        onClick = {
                            navController.navigate(
                                RoutesScreen.RouteDetails.createRoute(route.id)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
        }
    }
}

@Composable
fun RouteCard(
    route: Route,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val blackAndWhiteMatrix = ColorMatrix().apply { setToSaturation(0f) }

    val cardColor = if (route.status == Route.Status.SET) White else Grey5
    val imageFilter = if (route.status == Route.Status.SET)
        null
    else
        ColorFilter.colorMatrix(blackAndWhiteMatrix)

    val gradeColor = GradeLevels.gradeLevelToColor(route.gradeLevel)
    val gradeName = GradeLevels.gradeLevelToScaleString(
        route.gradeLevel, GradeLevels.GradeScale.FONT_SCALE
    )

    Card(
        modifier = modifier.height(96.dp),
        color = cardColor,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Row {
            Image(
                painter = rememberImagePainter(
                    data = route.pictureUrl,
                    builder = {
                        placeholder(R.drawable.placeholder_route)
                        error(R.drawable.placeholder_route)
                        fallback(R.drawable.placeholder_route)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(96.dp)
                    .fillMaxHeight(),
                colorFilter = imageFilter
            )

            Divider(
                color = Grey3,
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(gradeColor)
            )

            Divider(
                color = Grey3,
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = gradeName,
                        style = MaterialTheme.typography.h2,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TagsList(
                        tags = route.tags.map { it.value },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    if (route.status == Route.Status.TAKEN_DOWN)
                        Text(
                            text = stringResource(id = R.string.taken_down),
                            style = MaterialTheme.typography.body2.copy(Pink1),
                        )
                    else if (route.isNew)
                        Text(
                            text = stringResource(id = R.string.new_exclamation),
                            style = MaterialTheme.typography.body2.copy(Teal1),
                        )
                }

                Text(
                    text = stringResource(id = R.string.set_by_template, route.setterName),
                    style = MaterialTheme.typography.body2.copy(Grey2)
                )

                Spacer(modifier = Modifier.weight(1f))

                Divider(color = Grey4)

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = route.likesCount.toString(),
                        style = MaterialTheme.typography.caption.copy(Grey1)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like",
                        tint = Grey1,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = route.sendsCount.toString(),
                        style = MaterialTheme.typography.caption.copy(Grey1)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_arm),
                        contentDescription = "Send",
                        tint = Grey1,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = route.commentsCount.toString(),
                        style = MaterialTheme.typography.caption.copy(Grey1)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_message),
                        contentDescription = "Comments",
                        tint = Grey1,
                        modifier = Modifier.size(12.dp)
                    )

                    if (route.visualisationUrl != null) {
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_view_in_ar),
                            contentDescription = "Has 3D scene",
                            modifier = Modifier.size(12.dp),
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = route.setDate.toFormattedDateString(),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

