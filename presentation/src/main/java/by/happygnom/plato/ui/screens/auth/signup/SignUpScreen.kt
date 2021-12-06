package by.happygnom.plato.ui.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.theme.White

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        Image(
            painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.auth_background_faded)),
            contentDescription = "background",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                TealFilledButton(
                    text = "",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(230.dp)
                        .width(230.dp)
                        .clip(CircleShape),
                )
                Icon(
                    painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.logo)),
                    contentDescription = "logo",
                    tint = White
                )
            }

            Spacer(modifier = Modifier.height(100.dp))


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
                    text = "Sign up",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(30.dp, 0.dp)
                )
                TealStrokeButton(
                    text = "Login",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(30.dp, 0.dp)
                )
            }
        }

    }
}

