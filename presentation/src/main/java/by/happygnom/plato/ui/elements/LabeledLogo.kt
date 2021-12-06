package by.happygnom.plato.ui.elements

import android.util.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.happygnom.plato.R
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.Teal1

@Composable
fun LabeledLogo(
    modifier: Modifier = Modifier,
    size: Int = 40,
    spaceBetween: Dp = 8.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        androidx.compose.material.Card(
            modifier = Modifier
                .size(size.dp, size.dp)
                .clip(CircleShape),
            contentColor = Pink1,
            backgroundColor = Teal1,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.app_icon),
                "",
                modifier = Modifier
                    .padding(spaceBetween)
                    .align(Alignment.CenterVertically)
            )
        }

        Text(text = stringResource(R.string.plato_wide_text), fontSize = (size / 2).sp)
    }
}
