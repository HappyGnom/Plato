package by.happygnom.plato.ui.elements.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White

@Composable
fun LabeledIconButton(
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                focusManager.clearFocus()
                onClick()
            },
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = text,
                modifier = Modifier
                    .background(Teal1, CircleShape)
                    .fillMaxSize()
                    .padding(12.dp),
                tint = White
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}
