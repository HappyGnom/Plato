package by.happygnom.plato.ui.screens.routes.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.button.GreyFilledButton
import by.happygnom.plato.ui.elements.button.PinkFilledButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.ui.elements.inputs.QuantitySelector
import by.happygnom.plato.ui.elements.inputs.SimpleTextDropDown
import by.happygnom.plato.ui.elements.inputs.TealCheckbox
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.util.showDatePickerDialog
import by.happygnom.plato.util.toFormattedDateString
import java.util.*

@Composable
fun RoutesFilterScreen(
    viewModel: RoutesFilterViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            DefaultToolbar(
                text = stringResource(id = R.string.filters),
                startIconId = R.drawable.ic_back,
                onStartIconClick = { navController.popBackStack() }
            )
        }
    ) {
        RoutesFilterScreenContent(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun RoutesFilterScreenContent(
    viewModel: RoutesFilterViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val category by viewModel.category.observeAsState(null)
    val gradeLevelFrom by viewModel.gradeLevelFrom.observeAsState(GradeLevels.LOWEST_GRADE)
    val gradeLevelTo by viewModel.gradeLevelTo.observeAsState(GradeLevels.HIGHEST_GRADE)
    val setterName by viewModel.setterName.observeAsState("Unspecified")
    val setDateFrom by viewModel.setDateFrom.observeAsState(null)
    val setDateTo by viewModel.setDateTo.observeAsState(null)
    val includeTakenDown by viewModel.includeTakenDown.observeAsState(false)
    val tags by viewModel.tags.observeAsState("")

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(0.dp))

            SimpleTextDropDown(
                options = listOf(
                    stringResource(id = R.string.projected),
                    stringResource(id = R.string.sent),
                    stringResource(id = R.string.liked)
                ),
                selectedOption = category,
                onOptionChanged = viewModel::setCategory,
                modifier = Modifier.fillMaxWidth(0.8f).padding(16.dp),
                addUnspecifiedOption = true,
                label = stringResource(id = R.string.category)
            )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.grade_from),
                style = MaterialTheme.typography.body2
            )

            QuantitySelector(
                currentQuantity = gradeLevelFrom,
                onQuantityChange = viewModel::setGradeLevelFrom,
                quantityDisplayTransformation = { gradeLevel ->
                    GradeLevels.gradeLevelToScaleString(
                        gradeLevel, GradeLevels.GradeScale.FONT_SCALE
                    )
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.grade_to),
                style = MaterialTheme.typography.body2
            )

            QuantitySelector(
                currentQuantity = gradeLevelTo,
                onQuantityChange = viewModel::setGradeLevelTo,
                quantityDisplayTransformation = { gradeLevel ->
                    GradeLevels.gradeLevelToScaleString(
                        gradeLevel, GradeLevels.GradeScale.FONT_SCALE
                    )
                }
            )
        }

            SimpleTextDropDown(
                options = listOf(
                    "Yura Morozov",
                    "Ilya Khomyakov",
                    "Nika Malinovskaya",
                    "Igor Morozov",
                    "Nikita Logunov"
                ),
                selectedOption = setterName,
                onOptionChanged = viewModel::setSetterName,
                modifier = Modifier.fillMaxWidth(0.8f).padding(horizontal = 16.dp),
                addUnspecifiedOption = true,
                label = stringResource(id = R.string.setter_name)
            )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.set_date_from),
                style = MaterialTheme.typography.body2
            )

            val setDateText = if (setDateFrom == null)
                stringResource(id = R.string.unspecified)
            else
                setDateFrom!!.toFormattedDateString()

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TealStrokeButton(
                    text = setDateText,
                    onClick = {
                        val initialCalendar = Calendar.getInstance()
                        setDateFrom?.let { initialCalendar.time = it }

                        showDatePickerDialog(context, initialCalendar) { calendar ->
                            viewModel.setSetDateFrom(calendar.time)
                        }
                    },
                )

                if (setDateFrom != null)
                    IconButton(
                        onClick = { viewModel.clearSetDateFrom() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            tint = Grey1
                        )
                    }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.set_date_to),
                style = MaterialTheme.typography.body2
            )

            val setDateText = if (setDateTo == null)
                stringResource(id = R.string.unspecified)
            else
                setDateTo!!.toFormattedDateString()

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TealStrokeButton(
                    text = setDateText,
                    onClick = {
                        val initialCalendar = Calendar.getInstance()
                        setDateTo?.let { initialCalendar.time = it }

                        showDatePickerDialog(context, initialCalendar) { calendar ->
                            viewModel.setSetDateTo(calendar.time)
                        }
                    },
                )

                if (setDateTo != null)
                    IconButton(
                        onClick = { viewModel.clearSetDateTo() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            tint = Grey1
                        )
                    }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TealCheckbox(
                checked = includeTakenDown,
                onCheckedChange = viewModel::setIncludeTakenDown
            )

            Text(
                text = stringResource(id = R.string.include_taken_down),
                style = MaterialTheme.typography.body2
            )
        }

        InputTextFieldBox(
            text = tags,
            onValueChange = viewModel::setTags,
            label = stringResource(id = R.string.tags_list),
            hint = stringResource(id = R.string.tags),
//            error = errors?.tagsErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PinkFilledButton(
                text = stringResource(id = R.string.clear_all),
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
                    text = stringResource(id = R.string.apply),
                    onClick = { viewModel.applyFilter() },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
