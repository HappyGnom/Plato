package by.happygnom.plato.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import by.happygnom.plato.ui.theme.CardShape
import by.happygnom.plato.ui.theme.Pink1
import by.happygnom.plato.ui.theme.Pink2
import by.happygnom.plato.ui.theme.White

@Composable
fun PinkTag(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.caption,
    enabled: Boolean = false,
) {
    val tagColor = if (enabled) Pink1 else Pink2

    Box(
        modifier = modifier
            .background(tagColor, CardShape)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = style.copy(White)
        )
    }
}

@Composable
fun TagsList(
    tags: List<String>,
    modifier: Modifier = Modifier,
    displayMax: Int = 2,
    textStyle: TextStyle = MaterialTheme.typography.caption,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(tags.take(displayMax)) { tag ->
            PinkTag(
                text = tag,
                style = textStyle
            )
        }

        val overflowTagsCount = tags.size - displayMax
        if (overflowTagsCount > 0)
            item {
                PinkTag(
                    text = "+$overflowTagsCount",
                    style = textStyle
                )
            }
    }
}
