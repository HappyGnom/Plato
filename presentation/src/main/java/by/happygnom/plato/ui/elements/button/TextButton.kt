package by.happygnom.plato.ui.elements.button

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.happygnom.plato.R
import by.happygnom.plato.ui.theme.ButtonShape
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun TealTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    TextButton(
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        modifier = modifier,
        shape = ButtonShape,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2.copy(
                color = Teal1,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
