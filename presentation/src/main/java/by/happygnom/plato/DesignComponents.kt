package by.happygnom.plato

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.elements.TagsList
import by.happygnom.plato.ui.elements.button.*
import by.happygnom.plato.ui.elements.inputs.InputTextField
import by.happygnom.plato.ui.elements.inputs.QuantitySelector
import by.happygnom.plato.ui.theme.PlatoTheme
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun DesignComponents() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TagsList(tags = listOf("Strength", "Dynamic", "Crimp", "Tricky"), displayMax = 2)

            Text(text = "Buttons", style = MaterialTheme.typography.h1.copy(Teal1))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TealFilledButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                TealFilledButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PinkFilledButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                PinkFilledButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GreyFilledButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                GreyFilledButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TealStrokeButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                TealStrokeButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PinkStrokeButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                PinkStrokeButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GreyStrokeButton(text = "Continue", onClick = {}, modifier = Modifier.weight(1f))
                GreyStrokeButton(
                    text = "Continue",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StrokeImageButton(
                    text = "Google", imageId = R.drawable.ic_google,
                    onClick = {}, modifier = Modifier.weight(1f)
                )
                StrokeImageButton(
                    text = "Google", imageId = R.drawable.ic_google,
                    onClick = {}, enabled = false, modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Input field",
                style = MaterialTheme.typography.h1.copy(Teal1)
            )

            val textFieldValue = remember { mutableStateOf("") }
            InputTextField(
                text = textFieldValue.value,
                onValueChange = { textFieldValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                hint = "Email"
            )

            val passwordFieldValue = remember { mutableStateOf("") }
            InputTextField(
                text = passwordFieldValue.value,
                onValueChange = { passwordFieldValue.value = it },
                modifier = Modifier.fillMaxWidth(),
                hint = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enableVisibilityToggle = true
            )

            Text(
                text = "Quantity selection",
                style = MaterialTheme.typography.h1.copy(Teal1)
            )

            val quantity = remember { mutableStateOf(2) }
            QuantitySelector(
                currentQuantity = quantity.value,
                onQuantityChange = { quantity.value = it }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 720)
@Composable
fun DesignComponentsPreview() {
    PlatoTheme {
            DesignComponents()
    }
}
