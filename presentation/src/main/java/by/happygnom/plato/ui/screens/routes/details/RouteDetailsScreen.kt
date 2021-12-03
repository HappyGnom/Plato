package by.happygnom.plato.ui.screens.routes.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.domain.model.Route
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.AddCommentButton
import by.happygnom.plato.ui.elements.Comment
import by.happygnom.plato.ui.elements.TagsList
import by.happygnom.plato.ui.elements.button.SwitchableIconButton
import by.happygnom.plato.ui.elements.button.TealTextButton
import by.happygnom.plato.ui.navigation.RoutesScreen
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey3
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White
import me.onebone.toolbar.*

@Composable
fun RouteDetailsScreen(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    routeId: String,
) {
    viewModel.loadRoute(routeId)

    val toolbarState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        state = toolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        modifier = Modifier.fillMaxSize(),
        toolbarModifier = Modifier.background(Teal1),
        toolbar = {
            Toolbar(viewModel, navController, toolbarState)
        },
        body = {
            RouteDetails(viewModel, navController)
        }
    )
}

@Composable
fun CollapsingToolbarScope.Toolbar(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    toolbarState: CollapsingToolbarScaffoldState,
) {
    val gradeName by viewModel.gradeName.observeAsState("")

    Image(
        bitmap = ImageBitmap.imageResource(id = R.drawable.route_photo),
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
            .padding(16.dp)
            .size(24.dp)
            .pin()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = White
        )
    }

    Text(
        text = gradeName,
        style = MaterialTheme.typography.h3.copy(White),
        modifier = Modifier
            .padding(56.dp, 16.dp, 16.dp, 16.dp)
            .road(
                whenCollapsed = Alignment.CenterStart,
                whenExpanded = Alignment.BottomEnd
            )
    )
}

@Composable
fun RouteDetails(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
) {
    val resources = LocalContext.current.resources

    val isLiked by viewModel.isLiked.observeAsState(false)
    val isProjected by viewModel.isProjected.observeAsState(false)
    val isSent by viewModel.isSent.observeAsState(false)

    val route by viewModel.route.observeAsState()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TagsList(
                tags = route!!.tags,
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
                isActive = isProjected,
                onActiveStateChanged = viewModel::setProjected,
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
                        R.plurals.likes, route!!.likesCount, route!!.likesCount
                    ),
                    style = MaterialTheme.typography.caption.copy(Grey1)
                )

                Text(
                    text = resources.getQuantityString(
                        R.plurals.sends, route!!.sendsCount, route!!.sendsCount
                    ),
                    style = MaterialTheme.typography.caption.copy(Grey1)
                )
            }
        }

        Divider(color = Grey3)

        RouteInfo(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Divider(color = Grey3)

        LatestComments(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun RouteInfo(
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val route by viewModel.route.observeAsState()
    val gradeName by viewModel.gradeName.observeAsState("")

    val statusString = when (route!!.status) {
        Route.Status.SET -> stringResource(id = R.string.set)
        Route.Status.TAKEN_DOWN -> stringResource(id = R.string.taken_down)
    }

    val routeDetailsData = mapOf(
        stringResource(id = R.string.grade) to gradeName,
        stringResource(id = R.string.holds_color) to route!!.holdsColor,
        stringResource(id = R.string.set_by) to route!!.setterName,
        stringResource(id = R.string.set_date) to route!!.setDateString,
        stringResource(id = R.string.status) to statusString,
        stringResource(id = R.string.tags) to route!!.tags.joinToString()
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
    viewModel: RouteDetailsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val route by viewModel.route.observeAsState()

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
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            repeat(3) {
                Comment(modifier = Modifier.fillMaxWidth())
            }
        }

        TealTextButton(
            text = stringResource(id = R.string.see_all_comments_template, 5),
            onClick = { navController.navigate(RoutesScreen.Comments.createRoute(route!!.id)) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


