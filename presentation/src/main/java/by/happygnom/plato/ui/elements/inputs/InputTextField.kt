package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.happygnom.plato.R
import by.happygnom.plato.ui.theme.*

@Composable
fun InputTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enableVisibilityToggle: Boolean = false,
    singleLine: Boolean = false
) {
    var passwordIsShown by remember { mutableStateOf(false) }

    val visibilityToggleIconId = if (passwordIsShown)
        R.drawable.ic_eye_open
    else
        R.drawable.ic_eye_close

    val visualTransformation = if (enableVisibilityToggle && !passwordIsShown)
        PasswordVisualTransformation()
    else
        VisualTransformation.None

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = BlackNero,
        backgroundColor = Grey5,
        cursorColor = Teal1,
        unfocusedBorderColor = Grey3,
        focusedBorderColor = BlackNero,
        errorBorderColor = Pink1,
        trailingIconColor = Grey2
    )

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.body1,
        shape = InputFieldShape,
        colors = colors,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        modifier = modifier
            .focusable(true)
            .background(Grey5, InputFieldShape)
            .heightIn(1.dp, Dp.Infinity),
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.body1.copy(Grey2)
            )
        },
        trailingIcon = {
            if (enableVisibilityToggle)
                IconButton(onClick = { passwordIsShown = !passwordIsShown }) {
                    Icon(
                        painter = painterResource(id = visibilityToggleIconId),
                        contentDescription = null,
                        tint = Grey2
                    )
                }
        }
    )
}
