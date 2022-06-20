package by.happygnom.plato.ui.screens.auth.getStarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.LabeledLogo
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.navigation.AuthenticationScreen
import by.happygnom.plato.ui.theme.Teal1
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.*

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
                count = 3,
                state = pagerState,
//                itemSpacing = 32.dp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(1f),
//                contentPadding = PaddingValues(32.dp)
            ) { page ->

                Box(
                    modifier = Modifier
                        .fillMaxSize(1f)
                ) {
                    Box(
                        modifier = Modifier
//                            .border(1.dp, Teal1, RoundedCornerShape(4.dp))
                            .fillMaxSize(1f)
                    ) {
                        Image(
                            painter = BitmapPainter(
                                ImageBitmap.imageResource(
                                    when (page) {
                                        0 -> R.drawable.start_1
                                        1 -> R.drawable.start_2
                                        else -> R.drawable.start_3
                                    }
                                )
                            ),
                            contentDescription = "",
                            modifier = Modifier
//                                .padding(8.dp)
                                .padding(bottom = 64.dp)
                                .fillMaxSize(1f)
//                                .border(1.dp, Teal1, RoundedCornerShape(4.dp))

                        )
                    }

                    if (page == 2) {
                        TealFilledButton(text = "Sign up", onClick = {
                            navController.navigate(AuthenticationScreen.SignUp.route)
                        }, modifier = Modifier.align(Alignment.BottomCenter))
                    }

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
