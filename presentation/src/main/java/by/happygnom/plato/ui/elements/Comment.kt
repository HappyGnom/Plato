package by.happygnom.plato.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.happygnom.domain.model.Comment
import by.happygnom.plato.R
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey2
import by.happygnom.plato.util.toFormattedDateTimeString
import coil.compose.rememberImagePainter

@Composable
fun Comment(
    comment: Comment,
    modifier: Modifier = Modifier,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = comment.userPictureUrl,
                    builder = {
                        placeholder(R.drawable.placeholder_avatar)
                        error(R.drawable.placeholder_avatar)
                        fallback(R.drawable.placeholder_avatar)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = comment.userName,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    text = comment.dateTime.toFormattedDateTimeString(),
                    style = MaterialTheme.typography.caption.copy(Grey2)
                )
            }
        }

        Text(
            text = comment.message,
            style = MaterialTheme.typography.body2.copy(Grey1)
        )
    }
}

@Composable
fun AddCommentButton(
    onClick: () -> Unit,
    userImageUrl: String,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.clickable {
            focusManager.clearFocus()
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = userImageUrl,
                builder = {
                    placeholder(R.drawable.placeholder_avatar)
                    error(R.drawable.placeholder_avatar)
                    fallback(R.drawable.placeholder_avatar)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(id = R.string.add_public_comment),
            style = MaterialTheme.typography.body1.copy(Grey2)
        )
    }

}

