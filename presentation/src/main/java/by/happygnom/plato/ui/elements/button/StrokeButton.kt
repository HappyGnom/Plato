package by.happygnom.plato.ui.elements.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.elements.ColoredRippleTheme
import by.happygnom.plato.ui.theme.*

@Composable
fun TealStrokeButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit, enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Teal1, contentColor = BlackNero,
        disabledBackgroundColor = Teal2, disabledContentColor = Grey2
    )

    StrokeButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}

@Composable
fun PinkStrokeButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit, enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Pink1, contentColor = BlackNero,
        disabledBackgroundColor = Pink2, disabledContentColor = Grey2
    )

    StrokeButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}

@Composable
fun GreyStrokeButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit, enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Grey2, contentColor = BlackNero,
        disabledBackgroundColor = Grey2, disabledContentColor = Grey2
    )

    StrokeButton(
        text = text,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        buttonColors = colors
    )
}


@Composable
fun StrokeButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    buttonColors: ButtonColors
) {
    val focusManager = LocalFocusManager.current
    val rippleColor = buttonColors.backgroundColor(enabled = true).value

    CompositionLocalProvider(
        LocalRippleTheme provides ColoredRippleTheme(rippleColor),
    ) {
        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onClick()
            },
            modifier = modifier.height(40.dp),
            shape = ButtonShape,
            enabled = enabled,
            border = BorderStroke(1.dp, buttonColors.backgroundColor(enabled = enabled).value)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(
                    color = buttonColors.contentColor(enabled = enabled).value
                )
            )
        }
    }
}

