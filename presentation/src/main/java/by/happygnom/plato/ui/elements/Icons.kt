package by.happygnom.plato.ui.elements

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import by.happygnom.plato.R
import by.happygnom.plato.ui.theme.Grey1

@Composable
fun ArrowTrailingIcon(
    expanded: Boolean,
) {
    val iconId = if (expanded)
        R.drawable.ic_arrow_up
    else
        R.drawable.ic_arrow_down

    Icon(
        painter = painterResource(id = iconId),
        contentDescription = null,
        tint = Grey1
    )
}
