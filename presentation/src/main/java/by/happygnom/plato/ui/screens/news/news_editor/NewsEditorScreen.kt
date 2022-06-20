package by.happygnom.plato.ui.screens.news.news_editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
import by.happygnom.plato.navigation.ArgNames
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.elements.button.DatePickerButton
import by.happygnom.plato.ui.elements.button.GreyFilledButton
import by.happygnom.plato.ui.elements.button.PinkFilledButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.ui.theme.ButtonShape
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.White
import by.happygnom.plato.util.*
import java.util.*

@Composable
fun NewsEditorScreen(
    viewModel: NewsEditorViewModel,
    navController: NavController
) {
    val existingNewsId by viewModel.existingNewsId.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isDone by viewModel.isDone.observeAsState()

    isDone?.getContentIfNotHandled()?.let {
        navController.previousBackStackEntry?.savedStateHandle?.set(ArgNames.SHOULD_UPDATE, true)
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            val toolbarText = if (existingNewsId != null)
                stringResource(id = R.string.news_editing)
            else
                stringResource(id = R.string.news_creation)

            DefaultToolbar(
                text = toolbarText,
                startIconId = R.drawable.ic_back,
                onStartIconClick = { navController.popBackStack() }
            )
        }
    ) {
        NewsEditorScreenContent(
            viewModel = viewModel,
            navController = navController,
            existingNewsId = existingNewsId,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )

        if (isLoading)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
    }
}

@Composable
fun NewsEditorScreenContent(
    viewModel: NewsEditorViewModel,
    navController: NavController,
    existingNewsId: Long?,
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
    val header by viewModel.header.observeAsState("")
    val text by viewModel.text.observeAsState("")
    val publishDate by viewModel.publishDate.observeAsState(null)
    val publishTime by viewModel.publishTime.observeAsState(null)

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val bitmap = pictureBase64?.let {
            bitmapFromBase64(it).asImageBitmap()
        } ?: ImageBitmap.imageResource(id = R.drawable.placeholder_news)

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
                        .height(200.dp)
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

        if (existingNewsId != null)
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
                    text = existingNewsId.toString(),
                    style = MaterialTheme.typography.body2
                )

                ErrorText(errorId = null)
            }

        InputTextFieldBox(
            text = header,
            onValueChange = viewModel::setHeader,
            label = stringResource(id = R.string.news_header),
            isLabelAlwaysShown = true,
            hint = stringResource(id = R.string.header),
            error = errors?.headerErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        InputTextFieldBox(
            text = text,
            onValueChange = viewModel::setText,
            label = stringResource(id = R.string.news_content),
            isLabelAlwaysShown = true,
            singleLine = false,
            hint = stringResource(id = R.string.content),
            error = errors?.textErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        DatePickerButton(
            text = if (publishDate == null) "" else publishDate!!.toFormattedDateString(),
            hint = stringResource(id = R.string.select_date),
            label = stringResource(R.string.publish_date),
            isLabelAlwaysShown = true,
            onClick = {
                val initialCalendar = Calendar.getInstance()
                publishTime?.let { initialCalendar.time = it }

                showDatePickerDialog(context, initialCalendar) { calendar ->
                    viewModel.setPublishDate(calendar.time)
                }
            },
            error = errors?.publishDateErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClear = { viewModel.setPublishDate(null) }
        )

        DatePickerButton(
            text = if (publishTime == null) "" else publishTime!!.toFormattedTimeString(),
            hint = stringResource(id = R.string.select_time),
            label = stringResource(R.string.publish_time),
            isLabelAlwaysShown = true,
            onClick = {
                val initialCalendar = Calendar.getInstance()
                publishTime?.let { initialCalendar.time = it }

                showTimePickerDialog(context, initialCalendar) { calendar ->
                    viewModel.setPublishTime(calendar.time)
                }
            },
            error = errors?.publishTimeErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClear = { viewModel.setPublishTime(null) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (existingNewsId != null)
            PinkFilledButton(
                text = stringResource(id = R.string.unpublish),
                onClick = viewModel::deleteNews,
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
                onClick = viewModel::saveNews,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
