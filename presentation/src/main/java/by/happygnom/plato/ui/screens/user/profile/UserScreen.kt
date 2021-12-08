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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.AuthActivity
import by.happygnom.plato.R
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

    val mUser = FirebaseAuth.getInstance().currentUser

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Row(modifier = Modifier.height(120.dp)) {
            Image(
                painter = painterResource(R.drawable.placeholder_avatar),
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .width(120.dp)
                    .height(120.dp)

                    .border(width = 1.dp, color = Teal1, CircleShape)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Katerinka",
                    style = MaterialTheme.typography.h3
                )
                Text(text = "Zhevniak", style = MaterialTheme.typography.h3)
                Text(text = "(Nickname)", style = MaterialTheme.typography.body1)

            }
        }

        Divider(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(1f)
            ,
            color = Grey1
        )

        TealStrokeButton(modifier = Modifier.padding(top = 8.dp), text = "SignOut", onClick = {
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
