package by.happygnom.plato.ui.screens.routes.filter

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.domain.model.RoutesFilter
import by.happygnom.plato.R
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.elements.button.DatePickerButton
import by.happygnom.plato.ui.elements.button.GreyFilledButton
import by.happygnom.plato.ui.elements.button.PinkFilledButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.ui.elements.inputs.QuantitySelector
import by.happygnom.plato.ui.elements.inputs.SimpleTextDropDown
import by.happygnom.plato.ui.elements.inputs.TealCheckbox
import by.happygnom.plato.ui.navigation.ArgNames
import by.happygnom.plato.util.showDatePickerDialog
import by.happygnom.plato.util.toFormattedDateString
import java.util.*

@Composable
fun RoutesFilterScreen(
    viewModel: RoutesFilterViewModel,
    navController: NavController
) {
    val filterApplied by viewModel.filterApplied.observeAsState()
    filterApplied?.getContentIfNotHandled()?.let {
        navController.previousBackStackEntry?.savedStateHandle?.set(ArgNames.SHOULD_UPDATE, true)
        navController.popBackStack()
    }

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

    val setterOptions by viewModel.setterOptions.observeAsState(emptyList())

    val category by viewModel.category.observeAsState(null)
    val gradeLevelFrom by viewModel.gradeLevelFrom.observeAsState(GradeLevels.LOWEST_GRADE)
    val gradeLevelTo by viewModel.gradeLevelTo.observeAsState(GradeLevels.HIGHEST_GRADE)
    val setterName by viewModel.setterName.observeAsState(null)
    val setDateFrom by viewModel.setDateFrom.observeAsState(null)
    val setDateTo by viewModel.setDateTo.observeAsState(null)
    val includeTakenDown by viewModel.includeTakenDown.observeAsState(false)
    val tags by viewModel.tags.observeAsState("")

    val categoryStringsMapping = mapOf(
        RoutesFilter.Category.LIKED to stringResource(id = R.string.liked),
        RoutesFilter.Category.SENT to stringResource(id = R.string.sent),
        RoutesFilter.Category.BOOKMARKED to stringResource(id = R.string.bookmarked),
    )

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            SimpleTextDropDown(
                options = categoryStringsMapping.values.toList(),
                selectedOption = categoryStringsMapping[category],
                onOptionChanged = {
                    val categoryEnum = categoryStringsMapping.keys.find { key ->
                        categoryStringsMapping[key] == it
                    }
                    viewModel.setCategory(categoryEnum)
                },
                addUnspecifiedOption = true,
                label = stringResource(id = R.string.category),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            ErrorText(error = null)
        }

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

            ErrorText(error = null)
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

            ErrorText(error = null)
        }

        Column {
            SimpleTextDropDown(
                options = setterOptions,
                selectedOption = setterName,
                onOptionChanged = {
                    if (setterOptions.contains(it))
                        viewModel.setSetterName(it)
                    else
                        viewModel.setSetterName(null)
                },
                addUnspecifiedOption = true,
                label = stringResource(id = R.string.setter_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            ErrorText(error = null)
        }

        DatePickerButton(
            text = if (setDateFrom == null) "" else setDateFrom!!.toFormattedDateString(),
            hint = stringResource(id = R.string.select_date),
            label = stringResource(R.string.set_date_from),
            isLabelAlwaysShown = true,
            onClick = {
                val initialCalendar = Calendar.getInstance()
                setDateFrom?.let { initialCalendar.time = it }

                showDatePickerDialog(context, initialCalendar) { calendar ->
                    viewModel.setSetDateFrom(calendar.time)
                }
            },
            //error = errors?.setDateErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClear = { viewModel.clearSetDateFrom() }
        )

        DatePickerButton(
            text = if (setDateTo == null) "" else setDateTo!!.toFormattedDateString(),
            hint = stringResource(id = R.string.select_date),
            label = stringResource(R.string.set_date_to),
            isLabelAlwaysShown = true,
            onClick = {
                val initialCalendar = Calendar.getInstance()
                setDateTo?.let { initialCalendar.time = it }

                showDatePickerDialog(context, initialCalendar) { calendar ->
                    viewModel.setSetDateTo(calendar.time)
                }
            },
//                error = errors?.setDateErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClear = { viewModel.clearSetDateTo() }
        )

        Column {
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

            ErrorText(error = null)
        }

        InputTextFieldBox(
            text = tags,
            onValueChange = viewModel::setTags,
            label = stringResource(id = R.string.tags_list),
            isLabelAlwaysShown = true,
            hint = stringResource(id = R.string.tags),
//            error = errors?.tagsErrorId?.let { stringResource(id = it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PinkFilledButton(
                text = stringResource(id = R.string.clear_all),
                onClick = viewModel::resetFilter,
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
                    onClick = navController::popBackStack,
                    modifier = Modifier.weight(1f)
                )

                TealFilledButton(
                    text = stringResource(id = R.string.apply),
                    onClick = viewModel::applyFilter,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
