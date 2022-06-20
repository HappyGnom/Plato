package by.happygnom.plato.ui.screens.user.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.RadioButtonHorizontal
import by.happygnom.plato.ui.elements.button.DatePickerButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.navigation.UserScreen
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White
import by.happygnom.plato.util.GallerySelect
import by.happygnom.plato.util.showDatePickerDialog
import by.happygnom.plato.util.toFormattedDateString
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import me.onebone.toolbar.*
import java.util.*

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    val photoUrl by viewModel.photoUrl.observeAsState()
    val saved by viewModel.saved.observeAsState()

    saved?.getContentIfNotHandled()?.let {
        navController.navigate(UserScreen.Profile.route)
    }

    val toolbarState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        state = toolbarState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        modifier = Modifier.fillMaxSize(),
        toolbarModifier = Modifier
            .background(Teal1)
            .fillMaxWidth(),
        toolbar = {
            Toolbar(viewModel,navController, toolbarState, photoUrl)
        },
        body = {
            SettingsScreenContent(viewModel)
        }
    )
}


@Composable
fun CollapsingToolbarScope.Toolbar(
    viewModel: SettingsViewModel,
    navController: NavController,
    toolbarState: CollapsingToolbarScaffoldState,
    photoUrl: String?
) {
    var showGallerySelect by remember { mutableStateOf(false) }
    if (showGallerySelect) {
        GallerySelect(
            onImageUri = { uri ->
                showGallerySelect = false
                if (uri != null) viewModel.setPhotoUrl(uri.toString())
            }
        )
    }

    Image(
        painter = rememberImagePainter(
            data = photoUrl,
            builder = {
                placeholder(R.drawable.placeholder_avatar)
                error(R.drawable.placeholder_avatar)
                fallback(R.drawable.placeholder_avatar)
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

    IconButton(
        onClick = { showGallerySelect = true },
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
        text = stringResource(R.string.edit_my_profile),
        style = MaterialTheme.typography.h3.copy(White),
        modifier = Modifier
            .padding(56.dp, 16.dp, 16.dp, 16.dp)
            .clip(CircleShape)
            .background(Teal1)
            .padding(4.dp)
            .road(
                whenCollapsed = Alignment.CenterStart,
                whenExpanded = Alignment.BottomEnd
            )
    )
}

@Composable
fun SettingsScreenContent(viewModel: SettingsViewModel) {

    val context = LocalContext.current

    val errors by viewModel.errors.observeAsState()
    val name by viewModel.name.observeAsState("")
    val surname by viewModel.surname.observeAsState("")
    val nickname by viewModel.nickname.observeAsState("")
    val startDate by viewModel.startDate.observeAsState(null)
    val sex by viewModel.sex.observeAsState("Male")

    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .statusBarsPadding()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputTextFieldBox(
                    text = name,
                    onValueChange = viewModel::setName,
                    label = stringResource(R.string.user_name),
                    hint = stringResource(id = R.string.user_name),
                    error = errors?.nameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                )

                InputTextFieldBox(
                    text = surname,
                    onValueChange = viewModel::setSurname,
                    label = stringResource(R.string.user_surname),
                    hint = stringResource(id = R.string.user_surname),
                    error = errors?.surnameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                InputTextFieldBox(
                    text = nickname,
                    onValueChange = viewModel::setNickname,
                    label = stringResource(R.string.nickname),
                    hint = stringResource(id = R.string.nickname),
                    error = errors?.nicknameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                DatePickerButton(
                    text = startDate?.toFormattedDateString() ?: "",
                    hint = stringResource(R.string.start_date),
                    label = stringResource(R.string.start_date),
                    onClick = {
                        val initialCalendar = Calendar.getInstance()
                        startDate?.let { initialCalendar.time = it }

                        showDatePickerDialog(context, initialCalendar) { calendar ->
                            viewModel.setStartDate(calendar.time)
                        }
                    },
                    error = errors?.startDateErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onClear = { viewModel.setStartDate(null) }
                )

                val radioOptions: List<String> = listOf("Male", "Female", "Other")

                RadioButtonHorizontal(
                    label = "Sex",
                    radioOptions = radioOptions,
                    modifier = Modifier.padding(top = 8.dp),
                    onOptionSelected = {
                        viewModel.setSex(it)
                    },
                    selectedOption = sex
                )

                TealFilledButton(
                    text = stringResource(R.string.continueText),
                    onClick = {
                        viewModel.validateInput()
                        if (errors == null) {
                            viewModel.updateUser()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 32.dp)
                )
            }
        }
    }
}
