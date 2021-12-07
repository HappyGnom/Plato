package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.ArrowTrailingIcon
import by.happygnom.plato.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleTextDrop(
    options: List<String>,
    selectedOption: String?,
    onOptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    addUnspecifiedOption: Boolean = false
) {
    val dropdownOptions = options.toMutableList()
    if (addUnspecifiedOption)
        dropdownOptions.add(0, stringResource(id = R.string.unspecified))

    val dropdownSelectedOption = selectedOption ?: stringResource(id = R.string.unspecified)

    var expanded by remember { mutableStateOf(false) }
    val border = if (expanded)
        BorderStroke(1.dp, BlackNero)
    else
        BorderStroke(1.dp, Grey3)

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .border(border, ButtonShape),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = dropdownSelectedOption,
                        style = MaterialTheme.typography.body1.copy(Grey1),
                        modifier = Modifier.weight(1f)
                    )

                    ArrowTrailingIcon(expanded)
                }

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.clip(CardShape)
                ) {
                    dropdownOptions.filter { it != dropdownSelectedOption }.forEach { option ->
                        DropdownMenuItem(
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onOptionChanged(option)
                                expanded = false
                            },
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.body1.copy(Grey1)
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = " $label ",
            style = MaterialTheme.typography.body2.copy(Grey1),
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colors.surface),
            color = Grey1
        )
    }
}
