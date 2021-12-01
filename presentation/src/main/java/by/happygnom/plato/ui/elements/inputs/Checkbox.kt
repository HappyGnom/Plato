package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.Grey3
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White

@Composable
fun TealCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val colors = CheckboxDefaults.colors(
        checkedColor = Teal1,
        uncheckedColor = Grey3,
        checkmarkColor = White
    )

    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(20.dp),
        enabled = enabled,
        colors = colors
    )
}

@Composable
fun PinkCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val colors = CheckboxDefaults.colors(
        checkedColor = Pink1,
        uncheckedColor = Grey3,
        checkmarkColor = White
    )

    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(20.dp),
        enabled = enabled,
        colors = colors
    )
}
