package by.happygnom.plato.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey3
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.Teal2

@Composable
fun RadioButtonHorizontal(
    label: String,
    radioOptions: List<String>,
    modifier: Modifier = Modifier,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .border(width = 1.dp, color = Grey3, shape = RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            radioOptions.forEach { text ->
                Row(modifier = Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Teal1,
                            unselectedColor = Teal2
                        )
                    )
                    Text(text = text)
                }
            }
        }

        Text(
            text = " $label ",
            style = MaterialTheme.typography.body2.copy(Grey1),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colors.surface),
            color = Grey1
        )
    }


}

@Preview
@Composable
fun RadioButtonHorizontalPreview() {
    MaterialTheme {
        Scaffold() {
//            RadioButtonHorizontal(label = "hiiiiiiiii", radioOptions = listOf("First", "Second"))
        }
    }
}