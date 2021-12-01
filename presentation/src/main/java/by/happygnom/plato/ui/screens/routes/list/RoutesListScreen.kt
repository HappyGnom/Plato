package by.happygnom.plato.ui.screens.routes.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.mockRoutes
import by.happygnom.plato.R
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.Card
import by.happygnom.plato.ui.elements.button.LabeledIconButton
import by.happygnom.plato.ui.navigation.RoutesScreen
import by.happygnom.plato.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RoutesListScreen(
    listViewModel: RoutesListViewModel,
    navController: NavController,
) {
    val selectedIndex = rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.all_routes),
                style = MaterialTheme.typography.body1
            )

            IconButton(
                onClick = { navController.navigate(RoutesScreen.Filter.route) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    tint = Grey1
                )
            }
        }

        Divider(color = Grey3)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    LabeledIconButton(
                        text = stringResource(id = R.string.set),
                        iconPainter = painterResource(id = R.drawable.ic_path),
                        onClick = { /*TODO*/ }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.projects),
                        iconPainter = painterResource(id = R.drawable.ic_arm),
                        onClick = { /*TODO*/ }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.sent),
                        iconPainter = painterResource(id = R.drawable.ic_tick),
                        onClick = { /*TODO*/ }
                    )

                    LabeledIconButton(
                        text = stringResource(id = R.string.liked),
                        iconPainter = painterResource(id = R.drawable.ic_like),
                        onClick = { /*TODO*/ }
                    )
                }
            }

            items(mockRoutes) { route ->
                RouteCard(
                    route = route,
                    onClick = {},
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
    val dateFormat = rememberSaveable { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }
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
                painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.route_photo)),
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

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(route.tags) { tag ->
                            PinkTag(text = tag)
                        }
                    }

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
                        style = MaterialTheme.typography.caption
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like",
                        tint = Grey1,
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = dateFormat.format(route.setDate),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
fun PinkTag(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.caption,
    enabled: Boolean = false,
) {
    val tagColor = if (enabled) Pink1 else Pink2

    Box(
        modifier = modifier
            .background(tagColor, CardShape)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = style.copy(White)
        )
    }
}
