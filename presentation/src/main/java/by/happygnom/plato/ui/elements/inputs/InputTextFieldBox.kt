package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.theme.Grey1

@Composable
fun InputTextFieldBox(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    hint: String = "",
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enableVisibilityToggle: Boolean = false,
    singleLine: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2.copy(Grey1)
        )

        InputTextField(
            text = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            hint = hint,
            isError = error != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enableVisibilityToggle = enableVisibilityToggle,
            singleLine = singleLine
        )

        ErrorText(error = error)
    }
}

