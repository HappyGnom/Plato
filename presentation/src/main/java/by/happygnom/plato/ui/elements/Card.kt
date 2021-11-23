package by.happygnom.plato.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.CardShape
import by.happygnom.plato.ui.theme.Grey3
import by.happygnom.plato.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Card(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp, 4.dp),
    onClick: (() -> Unit)? = null,
    shape: Shape = CardShape,
    color: Color = White,
    elevation: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current
    val enabled = onClick != null

    Surface(
        onClick = {
            focusManager.clearFocus()
            (onClick ?: {}).invoke()
        },
        onClickLabel = null,
        indication = rememberRipple(),
        enabled = enabled,
        modifier = modifier,
        color = color,
        shape = shape,
        elevation = elevation,
        border = BorderStroke(1.dp, Grey3)
    ) {
        Box(modifier = modifier.padding(contentPadding), content = content)
    }
}
