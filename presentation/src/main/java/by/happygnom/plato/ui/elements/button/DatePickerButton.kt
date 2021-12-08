package by.happygnom.plato.ui.elements.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.ErrorText
import by.happygnom.plato.ui.theme.*

@Composable
fun DatePickerButton(
    text: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    label: String = "",
    isLabelAlwaysShown: Boolean = false,
    error: String? = null,
    onClick: () -> Unit,
    onClear: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = Grey3, contentColor = BlackNero,
        disabledBackgroundColor = Grey3, disabledContentColor = Grey2
    )

    val border = if(error==null)
        BorderStroke(1.dp, colors.backgroundColor(enabled = false).value)
    else
        BorderStroke(1.dp, Pink1)

    val iconId = when (text) {
        "" -> R.drawable.ic_arrow_down
        else -> R.drawable.ic_close
    }

    Column(modifier = modifier) {
        Box {
            OutlinedButton(
                onClick = {
                    focusManager.clearFocus()
                    onClick()
                },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .height(IntrinsicSize.Min),
                shape = ButtonShape,
                enabled = true,
                border = border,
//                colors = colors
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = if (text == "") hint else text,
                        style = MaterialTheme.typography.body1.copy(
                            color = when (text) {
                                "" -> colors.contentColor(enabled = false).value
                                else -> colors.contentColor(enabled = true).value
                            }
                        ),
                    )

                    Image(
                        painter = painterResource(id = iconId),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                when (text) {
                                    "" -> onClick()
                                    else -> onClear()
                                }
                            },
                    )
                }
            }

            Text(
                text = if (text != "" || isLabelAlwaysShown) " $label " else "",
                style = MaterialTheme.typography.body2.copy(Grey1),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.TopStart)
                    .background(MaterialTheme.colors.surface),
                color = if (error != null) Pink1 else Grey1
            )
        }

        ErrorText(error = error)
    }
}
