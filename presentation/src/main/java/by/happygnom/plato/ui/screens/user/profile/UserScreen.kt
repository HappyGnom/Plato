package by.happygnom.plato.ui.screens.user.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import by.happygnom.domain.model.User
import by.happygnom.plato.AuthActivity
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.button.TealStrokeButton
import coil.compose.rememberImagePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun UserScreen(
    viewModel: UserViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val webClientId = stringResource(R.string.default_web_client_id)

    val user = User("", " ", "", "", "", "", "")

    val mUser = FirebaseAuth.getInstance().currentUser
    mUser!!.getIdToken(true)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken: String? = task.result.token
                // Send token to your backend via HTTPS
                // ...

            } else {
                // Handle error -> task.getException();
            }
        }


    val signOut = {
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
    }

    Column {
        Row {
            Image(
                painter = painterResource(R.drawable.placeholder_avatar),
                contentDescription = "avatar"
            )

            Column() {
                Text(text = "Name")
            }

            TealStrokeButton(text = "SignOut", onClick = {
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
