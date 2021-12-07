package by.happygnom.plato.ui.screens.auth.getStarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.LabeledLogo
import by.happygnom.plato.ui.theme.Grey5
import by.happygnom.plato.ui.theme.Teal1
import by.happygnom.plato.ui.theme.Teal2
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GetStartedScreen(viewModel: GetStartedViewModel, navController: NavController) {
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {},
//                        modifier = Modifier.fillMaxSize(1f)
//                    ) {
//                        Icon(
//                            Icons.Rounded.KeyboardArrowLeft,
//                            "backIcon",
//                            modifier = Modifier.fillMaxSize(1f)
//                        )
//                    }
//                },
//                backgroundColor = Color.Transparent,
//                contentColor = MaterialTheme.colors.primary,
//                elevation = 0.dp,
//                modifier = Modifier
//                    .padding(top = 16.dp)
//                    .height(56.dp)
//                    .fillMaxWidth()
//            )
//        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pagerState = rememberPagerState()

            LabeledLogo(
                modifier = Modifier.padding(top = 56.dp),
                size = 58,
                spaceBetween = 16.dp
            )

            // Display 10 items
            HorizontalPager(
                count = 4,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(32.dp)
            ) { page ->

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = page.toString(),
                    )

                    Image(
                        painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.auth_background_faded)),
                        contentDescription = "",
//                        modifier = Modifier.fillMaxSize()
                        )

                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                inactiveColor = Teal1,
                spacing = 16.dp
            )

        }


    }
}

@Composable
fun Page() {

    Text(
        text = "Welcome back",
        modifier = Modifier.padding(top = 16.dp),
        style = MaterialTheme.typography.h2
    )
}