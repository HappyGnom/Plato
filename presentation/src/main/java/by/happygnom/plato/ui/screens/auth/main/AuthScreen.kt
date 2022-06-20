package by.happygnom.plato.ui.screens.auth.main

import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.navigation.AuthenticationScreen
import by.happygnom.plato.ui.theme.White
import com.google.accompanist.insets.navigationBarsPadding

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(viewModel: AuthViewModel, navController: NavController) {

    var selected by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (selected) 100.dp else 200.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .navigationBarsPadding()
    ) {
        Image(
            painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.auth_background_faded)),
            contentDescription = "background",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                val context = LocalContext.current
                val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://plato.by/")) }

                TealFilledButton(
                    text = "",
                    onClick = {
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(size)
                        .width(size)
                        .pointerInteropFilter {
                            when (it.action) {
                                MotionEvent.ACTION_BUTTON_PRESS -> {
                                    selected = true
                                }

                                MotionEvent.ACTION_BUTTON_RELEASE -> {
                                    selected = false
                                }
                            }
                            true
                        }
                        .shadow(
                            elevation = 32.dp,
                            shape = CircleShape,
                            clip = true
                        ),
                )
                Icon(
                    painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.logo)),
                    contentDescription = "logo",
                    tint = White,
                )
            }

            Spacer(modifier = Modifier.height(248.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                    .height(170.dp)
                    .background(White),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TealFilledButton(
                    text = "Get Started",
                    onClick = { navController.navigate(AuthenticationScreen.GetStarted.route) },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 32.dp)
                )
                TealStrokeButton(
                    text = "Login",
                    onClick = { navController.navigate(AuthenticationScreen.Login.route) },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}

