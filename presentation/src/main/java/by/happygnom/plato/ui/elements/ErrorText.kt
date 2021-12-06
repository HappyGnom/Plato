package by.happygnom.plato.ui.elements

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.happygnom.plato.ui.theme.Pink1

@Composable
fun ErrorText(
    @StringRes errorId: Int?,
    modifier: Modifier = Modifier
) {
    Text(
        text = errorId?.let { stringResource(id = errorId) } ?: "",
        style = MaterialTheme.typography.body2.copy(Pink1),
        modifier = modifier
    )
}

@Composable
fun ErrorText(
    error: String?,
    modifier: Modifier = Modifier
) {
    Text(
        text = error ?: "",
        style = MaterialTheme.typography.body2.copy(Pink1),
        modifier = modifier
    )
}
