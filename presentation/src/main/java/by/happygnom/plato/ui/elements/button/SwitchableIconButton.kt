package by.happygnom.plato.ui.elements.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource

@Composable
fun SwitchableIconButton(
    @DrawableRes activeIconResId: Int,
    @DrawableRes disabledIconResId: Int,
    isActive: Boolean,
    onActiveStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    val iconResId = if (isActive) activeIconResId else disabledIconResId

    val pulseAnimatedScale by animateFloatAsState(
        targetValue = if (isActive) 1.0f else 0f,
        animationSpec = keyframes {
            durationMillis = 250
            0.5f at 0 with FastOutSlowInEasing
            1.3f at 150 with FastOutLinearInEasing
        }
    )

    IconButton(
        modifier = modifier.scale(if (isActive) pulseAnimatedScale else 1f),
        enabled = enabled,
        onClick = {
            focusManager.clearFocus()
            onActiveStateChanged(!isActive)
        }) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
