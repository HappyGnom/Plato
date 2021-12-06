package by.happygnom.plato.ui.screens.routes.route_editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.elements.button.GreyFilledButton
import by.happygnom.plato.ui.elements.button.PinkFilledButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.ui.elements.inputs.QuantitySelector
import by.happygnom.plato.ui.theme.ButtonShape
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.White
import by.happygnom.plato.util.*
import java.util.*

@Composable
fun RouteEditorScreen(
    viewModel: RouteEditorViewModel,
    navController: NavController,
    existingRouteId: String?,
) {
    if (existingRouteId != null)
        viewModel.loadRouteData(existingRouteId)

    Scaffold(
        topBar = {
            val toolbarText = if (existingRouteId != null)
                stringResource(id = R.string.route_editing)
            else
                stringResource(id = R.string.route_creation)

            DefaultToolbar(
                text = toolbarText,
                startIconId = R.drawable.ic_back,
                onStartIconClick = { navController.popBackStack() }
            )
        }
    ) {
        RouteEditorScreenContent(
            viewModel = viewModel,
            navController = navController,
            existingRouteId = existingRouteId,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RouteEditorScreenContent(
    viewModel: RouteEditorViewModel,
    navController: NavController,
    existingRouteId: String?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    var showGallerySelect by remember { mutableStateOf(false) }
    if (showGallerySelect) {
        GallerySelect(
            onImageUri = { uri ->
                showGallerySelect = false
                if (uri != null) viewModel.setPicture(uri.toString())
            }
        )
    }

    var showTakePictureCamera by remember { mutableStateOf(false) }
    if (showTakePictureCamera) {
        CameraTakePicture(
            onImageUri = { uri ->
                showTakePictureCamera = false
                if (uri != null) viewModel.setPicture(uri.toString())
            }
        )
    }

    val errors by viewModel.errors.observeAsState()
    val pictureBase64 by viewModel.pictureBase64.observeAsState(null)
    val gradeLevel by viewModel.gradeLevel.observeAsState(0)
    val holdsColor by viewModel.holdsColor.observeAsState("")
    val setterName by viewModel.setterName.observeAsState("")
    val setDate by viewModel.setDate.observeAsState(null)
    val tags by viewModel.tags.observeAsState("")

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val bitmap = pictureBase64?.let {
            bitmapFromBase64(it).asImageBitmap()
        } ?: ImageBitmap.imageResource(id = R.drawable.placeholder_route)

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box {
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(ButtonShape)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    IconButton(
                        onClick = { showGallerySelect = true },
                        modifier = Modifier
                            .size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_gallery),
                            contentDescription = "Choose photo from gallery",
                            tint = White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Pink1, ButtonShape)
                                .padding(12.dp)
                        )
                    }

                    IconButton(
                        onClick = { showTakePictureCamera = true },
                        modifier = Modifier
                            .size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = "Take photo",
                            tint = White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Pink1, ButtonShape)
                                .padding(12.dp)
                        )
                    }
                }
            }

            ErrorText(errorId = errors?.pictureErrorId)
        }

        if (existingRouteId != null)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.id),
                    style = MaterialTheme.typography.body2.copy(Grey1)
                )

                Text(
                    text = existingRouteId.toString(),
                    style = MaterialTheme.typography.body2
                )

                ErrorText(errorId = null)
            }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.grade),
                style = MaterialTheme.typography.body2
            )

            QuantitySelector(
                currentQuantity = gradeLevel,
                onQuantityChange = viewModel::setGradeLevel,
                quantityDisplayTransformation = { gradeLevel ->
                    GradeLevels.gradeLevelToScaleString(
                        gradeLevel, GradeLevels.GradeScale.FONT_SCALE
                    )
                }
            )

            ErrorText(errorId = null)
        }

        InputTextFieldBox(
            text = holdsColor,
            onValueChange = viewModel::setHoldsColor,
            label = stringResource(id = R.string.holds_color),
            hint = stringResource(id = R.string.color),
            error = errors?.holdsColorErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        InputTextFieldBox(
            text = setterName,
            onValueChange = viewModel::setSetterName,
            label = stringResource(id = R.string.set_by),
            hint = stringResource(id = R.string.name),
            error = errors?.setterNameErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        InputTextFieldBox(
            text = tags,
            onValueChange = viewModel::setTags,
            label = stringResource(id = R.string.tags_list),
            hint = stringResource(id = R.string.tags),
            error = errors?.tagsErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.set_date),
                style = MaterialTheme.typography.body2
            )

            val setDateText = if (setDate == null)
                stringResource(id = R.string.select_date)
            else
                setDate!!.toFormattedString()

            TealStrokeButton(
                text = setDateText,
                onClick = {
                    val initialCalendar = Calendar.getInstance()
                    setDate?.let { initialCalendar.time = it }

                    showDatePickerDialog(context, initialCalendar) { calendar ->
                        viewModel.setSetDate(calendar.time)
                    }
                },
            )

            ErrorText(errorId = errors?.setDateErrorId)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (existingRouteId != null)
            PinkFilledButton(
                text = stringResource(id = R.string.take_down),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GreyFilledButton(
                text = stringResource(id = R.string.cancelation),
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )

            TealFilledButton(
                text = stringResource(id = R.string.confirm),
                onClick = { viewModel.validateInput() },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


