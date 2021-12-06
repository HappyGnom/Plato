package by.happygnom.plato.ui.elements.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.elements.ColoredRippleTheme
import by.happygnom.plato.ui.theme.ButtonShape
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey2

@Composable
fun StrokeImageButton(
    text: String,
    @DrawableRes imageId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Grey2, contentColor = Grey1,
        disabledBackgroundColor = Grey2, disabledContentColor = Grey2
    )
    val rippleColor = colors.backgroundColor(enabled = true).value

    val imageColorFilter = if (enabled) null
    else ColorFilter.tint(Grey2)

    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(
        LocalRippleTheme provides ColoredRippleTheme(rippleColor),
    ) {
        OutlinedButton(
            onClick = {
                focusManager.clearFocus()
                onClick()
            },
            modifier = modifier,
            shape = ButtonShape,
            enabled = enabled,
            border = BorderStroke(1.dp, colors.backgroundColor(enabled = enabled).value)
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(),
                    colorFilter = imageColorFilter
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.button.copy(
                        color = colors.contentColor(enabled = enabled).value
                    )
                )
            }
        }
    }
}
