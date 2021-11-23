package by.happygnom.plato.ui.elements.button

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.*

@Composable
fun TealFilledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Teal1, contentColor = White,
        disabledBackgroundColor = Teal2, disabledContentColor = White
    )

    FilledButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}

@Composable
fun PinkFilledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Pink1, contentColor = White,
        disabledBackgroundColor = Pink2, disabledContentColor = White
    )

    FilledButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}

@Composable
fun GreyFilledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Grey3, contentColor = BlackNero,
        disabledBackgroundColor = Grey3, disabledContentColor = Grey2
    )

    FilledButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}

@Composable
fun FilledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    buttonColors: ButtonColors
) {
    val focusManager = LocalFocusManager.current

    Button(
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        modifier = modifier.height(40.dp),
        shape = ButtonShape,
        enabled = enabled,
        colors = buttonColors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button.copy(
                color = buttonColors.contentColor(enabled = enabled).value
            )
        )
    }
}
