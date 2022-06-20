package by.happygnom.plato.ui.screens.user.profile

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.AuthActivity
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import by.happygnom.plato.navigation.UserScreen
import by.happygnom.plato.ui.theme.*
import by.happygnom.plato.util.toFormattedDateString
import coil.compose.rememberImagePainter


@Composable
fun UserScreen(
    viewModel: UserViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            val profileText = stringResource(R.string.my_profile)

            DefaultToolbar(
                text = profileText,
                endIconId = R.drawable.ic_settings,
                onEndIconClick = { navController.navigate(UserScreen.Settings.route) }
            )
        },
    ) {
        UserScreenContent()
    }

}

@Composable
fun UserScreenContent() {
    val context = LocalContext.current
    val webClientId = stringResource(R.string.default_web_client_id)

    val user = AuthenticatedUser.get()

    fun sexToString(sex: Int?): String? {
        return when (sex) {
            0 -> "Male"
            1 -> "Female"
            2 -> "Other"
            else -> null
        }
    }

    val scrollState = rememberScrollState()

    // Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { scrollState.animateScrollTo(0) }

    Box(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxSize(1f)
    ) {
        Column(
            modifier = Modifier
//                .padding(bottom = 8.dp)
                .verticalScroll(scrollState)
                .fillMaxSize(1f)
                .border(0.dp, Color.Transparent, RoundedCornerShape(4.dp))
                .shadow(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Image(
                        rememberImagePainter(
                            data = user?.pictureUrl,
                            builder = {
                                placeholder(R.drawable.placeholder_avatar)
                                error(R.drawable.placeholder_avatar)
                                fallback(R.drawable.placeholder_avatar)
                            }
                        ),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .border(width = 1.dp, color = Teal1, CircleShape)
                            .clip(CircleShape)
                            .shadow(2.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .fillMaxHeight(1f)
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    InputTextFieldBox(
                        text = user?.name ?: "",
                        label = "Name:\t",
                        hint = "Undefined",
                        onValueChange = {},
                        enabled = false
                    )
                    InputTextFieldBox(
                        text = user?.surname ?: "",
                        label = "Surname:\t",
                        hint = "Undefined",
                        onValueChange = {},
                        enabled = false
                    )
                    InputTextFieldBox(
                        text = user?.nickname ?: "",
                        label = "Nickname:\t",
                        hint = "Undefined",
                        onValueChange = {},
                        enabled = false
                    )
                    InputTextFieldBox(
                        text = sexToString(user?.sex) ?: "",
                        label = "Sex:\t",
                        hint = "Undefined",
                        onValueChange = {},
                        enabled = false
                    )
                    InputTextFieldBox(
                        text = user?.startDate?.toFormattedDateString() ?: "",
                        label = "Started climbing:\t",
                        hint = "Undefined",
                        onValueChange = {},
                        enabled = false
                    )
                }
            }

            TealFilledButton(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "SignOut",
                onClick = {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(webClientId)
                            .requestEmail()
                            .build()
                    val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
                    Firebase.auth.signOut()
                    mGoogleSignInClient.signOut().addOnCompleteListener {
                        val intent = Intent(context, AuthActivity::class.java)
                        context.startActivity(intent)
                    }
                })
        }
    }
}
