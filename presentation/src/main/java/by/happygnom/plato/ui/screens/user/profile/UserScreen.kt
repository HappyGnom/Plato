package by.happygnom.plato.ui.screens.user.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.AuthActivity
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.ui.elements.DefaultToolbar
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import by.happygnom.plato.ui.navigation.RoutesScreen
import by.happygnom.plato.ui.theme.Teal1
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import by.happygnom.plato.ui.navigation.UserScreen
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey5
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

    Column(
        modifier = Modifier.padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(1f)) {
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
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .padding(horizontal = 40.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(text = "Name:\t" + user?.name, style = MaterialTheme.typography.h3)
                Text(text = "Surname:\t" + user?.surname, style = MaterialTheme.typography.h3)
                Text(text = "Nickname:\t" + user?.nickname, style = MaterialTheme.typography.h3)
                Text(text = "Sex:\t" + user?.sex, style = MaterialTheme.typography.h3)
                Text(
                    text = "Started climbing:\t" + user?.startDate!!.toFormattedDateString(),
                    style = MaterialTheme.typography.h3
                )

            }
        }

        TealStrokeButton(
            modifier = Modifier.padding(vertical = 32.dp),
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
