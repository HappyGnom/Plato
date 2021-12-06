package by.happygnom.plato.ui.elements.button

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.White

@Composable
fun AddFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    FloatingActionButton(
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        backgroundColor = Pink1,
        contentColor = White,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add route"
        )
    }
}
