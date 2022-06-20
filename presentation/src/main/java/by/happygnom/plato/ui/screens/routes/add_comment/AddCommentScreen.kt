package by.happygnom.plato.ui.screens.routes.add_comment

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.LoadingIndicator
import by.happygnom.plato.ui.elements.inputs.InputTextField
import by.happygnom.plato.navigation.ArgNames
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey2
import by.happygnom.plato.ui.theme.Teal1
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun AddCommentScreen(
    viewModel: AddCommentViewModel,
    navController: NavController,
) {
    val commentPublished by viewModel.commentPublished.observeAsState()
    commentPublished?.getContentIfNotHandled()?.let {
        navController.previousBackStackEntry?.savedStateHandle?.set(ArgNames.SHOULD_UPDATE, true)
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            DefaultToolbar(
                text = stringResource(id = R.string.add_comment),
                startIconId = R.drawable.ic_back,
                onStartIconClick = { navController.popBackStack() }
            )
        }
    ) {
        AddCommentScreenContent(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun AddCommentScreenContent(
    viewModel: AddCommentViewModel,
    modifier: Modifier = Modifier,
) {
    val commentContent by viewModel.commentContent.observeAsState("")
    val isLoading by viewModel.isLoading.observeAsState(false)

    val sendButtonEnabled = commentContent.isNotBlank()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = {},
        indicator = { state, refreshTrigger -> LoadingIndicator(state, refreshTrigger) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.add_comment_info),
                style = MaterialTheme.typography.body1.copy(Grey1)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InputTextField(
                    text = commentContent,
                    onValueChange = {
                        if (!isLoading)
                            viewModel.setCommentContent(it)
                    },
                    modifier = Modifier.weight(1f),
                    hint = stringResource(id = R.string.add_public_comment),
                    singleLine = false
                )

                IconButton(
                    onClick = viewModel::publishComment,
                    enabled = sendButtonEnabled,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        tint = if (sendButtonEnabled) Teal1 else Grey2,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
