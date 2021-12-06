package by.happygnom.plato.ui.elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White

@Composable
fun DefaultToolbar(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes startIconId: Int? = null,
    onStartIconClick: () -> Unit = {},
    @DrawableRes endIconId: Int? = null,
    onEndIconClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(Teal1)
            .padding(13.dp)
    ) {
        if (startIconId != null)
            IconButton(
                onClick = onStartIconClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = startIconId),
                    contentDescription = null,
                    tint = White
                )
            }

        Text(
            text = text,
            style = MaterialTheme.typography.h3.copy(White),
            modifier = Modifier.weight(1f)
        )

        if (endIconId != null)
            IconButton(
                onClick = onEndIconClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = endIconId),
                    contentDescription = null,
                    tint = White
                )
            }
    }
}
