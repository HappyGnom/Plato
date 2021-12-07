package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.theme.BlackNero
import by.happygnom.plato.ui.theme.GradeRed
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Teal2

@Composable
fun InputTextFieldBox(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    hint: String = "",
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enableVisibilityToggle: Boolean = false,
    singleLine: Boolean = true
) {
    Box(modifier = modifier) {
        Column {
            InputTextField(
                text = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                hint = hint,
                isError = error != null,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                enableVisibilityToggle = enableVisibilityToggle,
                singleLine = singleLine
            )

            ErrorText(error = error)
        }

        Text(
            text = if (text != "") " $label " else "",
            style = MaterialTheme.typography.body2.copy(Grey1),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colors.surface),
            color = if (error != null) GradeRed else Grey1
        )

    }
}

@Preview
@Composable
fun InputBoxPreview() {
    val text = remember { mutableStateOf("") }
    MaterialTheme {
        Scaffold(modifier = Modifier.padding(24.dp)) {
            InputTextFieldBox(
                text = text.value,
                label = "label",
                hint = "hint",
                error = "error",
                onValueChange = { text.value = it })
        }
    }
}

