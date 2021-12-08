package by.happygnom.plato.ui.screens.routes.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import by.happygnom.domain.model.Comment
import by.happygnom.domain.model.Route
import by.happygnom.plato.R
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.AddCommentButton
import by.happygnom.plato.ui.elements.Comment
import by.happygnom.plato.ui.elements.LoadingIndicator
import by.happygnom.plato.ui.elements.TagsList
import by.happygnom.plato.ui.elements.button.StrokeImageButton
import by.happygnom.plato.ui.elements.button.SwitchableIconButton
import by.happygnom.plato.ui.elements.button.TealTextButton
import by.happygnom.plato.ui.navigation.ArgNames
import by.happygnom.plato.ui.navigation.RoutesScreen
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey3
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White
import by.happygnom.plato.util.build3dModelIntent
import by.happygnom.plato.util.toFormattedDateString
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.onebone.toolbar.*

@Composable
fun RouteDetailsScreen(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(ArgNames.SHOULD_UPDATE)
        ?.observe(LocalLifecycleOwner.current) {
            viewModel.loadRouteDetails(true)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Boolean>(ArgNames.SHOULD_UPDATE)
        }

    val route by viewModel.route.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)

    val gradeName = route?.let {
        GradeLevels.gradeLevelToScaleString(
            it.gradeLevel, GradeLevels.GradeScale.FONT_SCALE
        )
    }

    val toolbarState = rememberCollapsingToolbarScaffoldState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { viewModel.loadRouteDetails(true) },
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        CollapsingToolbarScaffold(
            state = toolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            modifier = Modifier.fillMaxSize(),
            toolbarModifier = Modifier
                .background(Teal1)
                .fillMaxWidth(),
            toolbar = {
                Toolbar(navController, toolbarState, route, gradeName)
            },
            body = {
                RouteDetailsScreenContent(
                    viewModel = viewModel,
                    navController = navController,
                    toolbarState = toolbarState,
                    route = route,
                    gradeName = gradeName
                )
            }
        )
    }
}

@Composable
fun CollapsingToolbarScope.Toolbar(
    navController: NavController,
    toolbarState: CollapsingToolbarScaffoldState,
    route: Route?,
    gradeName: String?
) {
    Image(
        painter = rememberImagePainter(
            data = route?.pictureUrl,
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
            .aspectRatio(1f)
            .parallax(0.2f)
            .graphicsLayer {
                // change alpha of Image as the toolbar expands
                alpha = toolbarState.toolbarState.progress
            }
    )

    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(10.dp)
            .clip(CircleShape)
            .background(Teal1)
            .padding(6.dp)
            .size(24.dp)
            .road(
                whenCollapsed = Alignment.TopStart,
                whenExpanded = Alignment.TopStart
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = White
        )
    }

    if (route != null)
        IconButton(
            onClick = { navController.navigate(RoutesScreen.Editor.createRoute(route.id)) },
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .background(Teal1)
                .padding(6.dp)
                .size(24.dp)
                .road(
                    whenCollapsed = Alignment.TopEnd,
                    whenExpanded = Alignment.TopEnd
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                tint = White
            )
        }

    Text(
        text = gradeName ?: stringResource(id = R.string.route_info),
        style = MaterialTheme.typography.h3.copy(White),
        modifier = Modifier
            .padding(56.dp, 16.dp, 16.dp, 16.dp)
            .clip(CircleShape)
            .background(Teal1)
            .padding(4.dp)
            .road(
                whenCollapsed = Alignment.CenterStart,
                whenExpanded = if (route != null) Alignment.BottomEnd else Alignment.CenterStart
            )
    )
}

@OptIn(ExperimentalToolbarApi::class)
@Composable
fun RouteDetailsScreenContent(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    toolbarState: CollapsingToolbarScaffoldState,
    route: Route?,
    gradeName: String?,
) {
    val latestComments by viewModel.latestComments.observeAsState(emptyList())

    when {
        route == null || gradeName == null ->
            Text(
                text = stringResource(id = R.string.failed_to_load_route),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
        else -> {
//            LaunchedEffect(key1 = "Open image", block = {
//                toolbarState.toolbarState.expand(100)
//            })

            RouteDetails(
                viewModel = viewModel,
                navController = navController,
                route = route,
                gradeName = gradeName,
                latestComments = latestComments
            )
        }
    }
}

@Composable
fun RouteDetails(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    route: Route,
    gradeName: String,
    latestComments: List<Comment>
) {
    val resources = LocalContext.current.resources

    val isLiked by viewModel.isLiked.observeAsState(false)
    val isBookmarked by viewModel.isBookmarked.observeAsState(false)
    val isSent by viewModel.isSent.observeAsState(false)

    val manualLikesCountChange by viewModel.manualLikesCountChange.observeAsState(0)
    val manualSendsCountChange by viewModel.manualSendsCountChange.observeAsState(0)

    val displayLikesCount = route.likesCount + manualLikesCountChange
    val displaySendsCount = route.sendsCount + manualSendsCountChange

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TagsList(
                tags = route.tags.map { it.value },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )

            SwitchableIconButton(
                activeIconResId = R.drawable.ic_arm_filled,
                disabledIconResId = R.drawable.ic_arm,
                isActive = isSent,
                onActiveStateChanged = viewModel::setSent,
                modifier = Modifier.size(24.dp)
            )

            SwitchableIconButton(
                activeIconResId = R.drawable.ic_bookmark_filled,
                disabledIconResId = R.drawable.ic_bookmark,
                isActive = isBookmarked,
                onActiveStateChanged = viewModel::setBookmarked,
                modifier = Modifier.size(24.dp)
            )

            SwitchableIconButton(
                activeIconResId = R.drawable.ic_like_filled,
                disabledIconResId = R.drawable.ic_like,
                isActive = isLiked,
                onActiveStateChanged = viewModel::setLiked,
                modifier = Modifier.size(24.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = resources.getQuantityString(
                        R.plurals.likes, displayLikesCount, displayLikesCount
                    ),
                    style = MaterialTheme.typography.caption.copy(Grey1)
                )

                Text(
                    text = resources.getQuantityString(
                        R.plurals.sends, displaySendsCount, displaySendsCount
                    ),
                    style = MaterialTheme.typography.caption.copy(Grey1)
                )
            }
        }

        Divider(color = Grey3)

        if (route.visualisationUrl != null) {
            Route3dVisualisation(
                routeVisualisationUrl = route.visualisationUrl!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Divider(color = Grey3)
        }

        RouteInfo(
            route = route,
            gradeName = gradeName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Divider(color = Grey3)

        LatestComments(
            navController = navController,
            route = route,
            latestComments = latestComments,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun Route3dVisualisation(
    routeVisualisationUrl: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.visualisation),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        StrokeImageButton(
            text = stringResource(id = R.string.view_3d),
            imageId = R.drawable.ic_view_in_ar,
            onClick = {
                val sceneViewerIntent = build3dModelIntent(routeVisualisationUrl)
                ContextCompat.startActivity(context, sceneViewerIntent, null)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun RouteInfo(
    route: Route,
    gradeName: String,
    modifier: Modifier = Modifier
) {
    val statusString = when (route.status) {
        Route.Status.SET -> stringResource(id = R.string.set)
        Route.Status.TAKEN_DOWN -> stringResource(id = R.string.taken_down)
    }

    val routeDetailsData = mapOf(
        stringResource(id = R.string.grade) to gradeName,
        stringResource(id = R.string.holds_color) to route.holdsColor,
        stringResource(id = R.string.set_by) to route.setterName,
        stringResource(id = R.string.set_date) to route.setDate.toFormattedDateString(),
        stringResource(id = R.string.status) to statusString,
        stringResource(id = R.string.tags) to route.tags.joinToString()
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.route_info),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        routeDetailsData.forEach {
            RouteInfoRow(
                label = it.key,
                value = it.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RouteInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.body2.copy(Grey1)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun LatestComments(
    navController: NavController,
    route: Route,
    latestComments: List<Comment>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.comments),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AddCommentButton(
                onClick = { navController.navigate(RoutesScreen.AddComment.createRoute(route.id)) },
                userImageUrl = "https://i.imgur.com/SWyt4bv.jpeg",
                modifier = Modifier.fillMaxWidth()
            )

            if (latestComments.isNullOrEmpty())
                Text(
                    text = stringResource(id = R.string.no_comments),
                    style = MaterialTheme.typography.body1
                )
            else
                latestComments.forEach {
                    Comment(it, modifier = Modifier.fillMaxWidth())
                }
        }

        TealTextButton(
            text = stringResource(id = R.string.see_all_comments_template, route.commentsCount),
            onClick = { navController.navigate(RoutesScreen.Comments.createRoute(route.id)) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
