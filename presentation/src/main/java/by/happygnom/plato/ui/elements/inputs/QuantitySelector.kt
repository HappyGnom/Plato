package by.happygnom.plato.ui.elements.inputs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import by.happygnom.plato.ui.theme.ButtonShape
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey3

@Composable
fun QuantitySelector(
    currentQuantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    quantityDisplayTransformation: (Int) -> String = { it.toString() }
) {
    val focusManager = LocalFocusManager.current

    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = Grey3, contentColor = Grey1
    )

    ConstraintLayout(
        modifier = modifier
    ) {
        val (leftButton, label, rightButton) = createRefs()

        Button(
            onClick = {
                focusManager.clearFocus()
                onQuantityChange(currentQuantity - 1)
            },
            modifier = modifier
                .defaultMinSize(20.dp, 20.dp)
                .size(24.dp, 24.dp)
                .constrainAs(leftButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentPadding = PaddingValues(4.dp),
            shape = ButtonShape,
            colors = buttonColors
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.body2.copy(
                    color = buttonColors.contentColor(enabled = true).value
                )
            )
        }

        Text(
            text = quantityDisplayTransformation(currentQuantity),
            modifier = Modifier.constrainAs(label) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                focusManager.clearFocus()
                onQuantityChange(currentQuantity + 1)
            },
            modifier = modifier
                .defaultMinSize(20.dp, 20.dp)
                .size(24.dp, 24.dp)
                .constrainAs(rightButton) {
                    top.linkTo(parent.top)
                    start.linkTo(leftButton.end, 40.dp)
                },
            contentPadding = PaddingValues(4.dp), shape = ButtonShape,
            colors = buttonColors
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.body2.copy(
                    color = buttonColors.contentColor(enabled = true).value
                )
            )
        }
    }
}
