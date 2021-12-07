package by.happygnom.plato.ui.elements

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.White
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun LoadingIndicator(state: SwipeRefreshState, refreshTrigger: Dp) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = refreshTrigger,
        scale = true,
        backgroundColor = White,
        contentColor = Teal1,
        shape = CircleShape,
    )
}
