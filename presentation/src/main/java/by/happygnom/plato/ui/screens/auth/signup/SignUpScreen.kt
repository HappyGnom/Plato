package by.happygnom.plato.ui.screens.auth.signup

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.happygnom.plato.MainActivity
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.LabeledLogo
import by.happygnom.plato.ui.elements.button.StrokeImageButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextField
import by.happygnom.plato.ui.navigation.AuthenticationScreen
import by.happygnom.plato.ui.screens.auth.main.AuthViewModel
import by.happygnom.plato.ui.theme.*
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Composable
fun SignUpScreen(viewModel: AuthViewModel, navController: NavController) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userPasswordConfirm by remember { mutableStateOf("") }
    val error by viewModel.error.observeAsState("")
    val isSignedIn by viewModel.signedIn.observeAsState(false)
    val loading by viewModel.loading.observeAsState(false)

    val auth: FirebaseAuth = Firebase.auth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var authResultLauncher: ActivityResultLauncher<Intent>
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    fun updateUI() {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    if (isSignedIn) {
        updateUI()
    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(R.string.default_web_client_id))
        .requestEmail()
        .build()
    mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

    authResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    viewModel.firebaseAuthWithGoogle(account.idToken!!)
                    coroutineScope.launch {
                        viewModel.signIn(
                            email = account.email,
                            displayName = account.displayName,
                        )
                    }
                }
            } catch (e: ApiException) {
                Log.e(e.toString(), "Google sign in failed")
            }
        }


    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        authResultLauncher.launch(signInIntent)
    }

    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .statusBarsPadding()
        ) {

            Divider(
                Modifier
                    .fillMaxWidth(1f)
                    .width(1.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LabeledLogo()

                Text(
                    text = "Create an account",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.h2
                )

                InputTextField(
                    text = userEmail,
                    hint = "Enter your email",
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(1f),
                    singleLine = true,
                    onValueChange = {
                        userEmail = it
                    }
                )

                InputTextField(
                    text = userPassword,
                    hint = "Enter your password",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(1f),
                    singleLine = true,
                    enableVisibilityToggle = true,
                    onValueChange = {
                        userPassword = it
                    },
                )

                InputTextField(
                    text = userPasswordConfirm,
                    hint = "Confirm your password",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(1f),
                    singleLine = true,
                    enableVisibilityToggle = true,
                    onValueChange = {
                        userPasswordConfirm = it
                    },
                )

                if (error != "") {
                    Text(
                        text = error, style = MaterialTheme.typography.h6.copy(GradeRed),
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                TealFilledButton(
                    text = "SignUp",
                    enabled = userEmail.isNotEmpty() &&
                            userPassword.isNotEmpty() &&
                            userPasswordConfirm.isNotEmpty(),
                    onClick = {
                        viewModel.signUpWithEmailAndPassword(
                            userEmail.trim(),
                            userPassword.trim(),
                            userPassword.trim()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 32.dp)
                )

                val signUpAnnotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.finlandica_regular,
                                    FontWeight.Normal
                                )
                            )
                        )
                    ) {
                        append("Already have a PLATO account?   ")
                    }
                    pushStringAnnotation(
                        tag = "SignIn",
                        annotation = "SignIn"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Teal1,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.finlandica_regular))
                        )
                    ) {
                        append("SignIn")
                    }
                    pop()
                }

                ClickableText(
                    text = signUpAnnotatedText,
                    onClick = { offset ->
                        signUpAnnotatedText.getStringAnnotations(
                            tag = "SignIn",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            navController.navigate(AuthenticationScreen.Login.route)
                        }
                    },
                    modifier = Modifier.padding(top = 24.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Divider(modifier = Modifier.weight(2f))
                    Text(
                        text = "OR",
                        style = MaterialTheme.typography.h3.copy(Grey1),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Divider(modifier = Modifier.weight(2f))
                }

                StrokeImageButton(
                    text = "Sign up with Google",
                    imageId = R.drawable.ic_google,
                    onClick = {
                        if (auth.currentUser == null) {
                            signInWithGoogle()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(1f)
                )

                val termsOfUseAnnotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Grey2,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.finlandica_regular))
                        )
                    ) {
                        append("By using PLATO you agree to our ")
                    }
                    pushStringAnnotation(
                        tag = "TermsOfUse",
                        annotation = "TermsOfUse"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Grey2,
                            fontStyle = FontStyle.Italic,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.finlandica_regular,
                                    FontWeight.Normal
                                )
                            )
                        )
                    ) {
                        append("Terms of Use")
                    }
                    pop()

                    withStyle(
                        style = SpanStyle(
                            color = Grey2,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.finlandica_regular))
                        )
                    ) {
                        append(" and ")
                    }

                    pushStringAnnotation(
                        tag = "PrivacyPolicy",
                        annotation = "PrivacyPolicy"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Grey2,
                            fontStyle = FontStyle.Italic,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.finlandica_regular,
                                    FontWeight.Normal
                                )
                            ),
                        )
                    ) {
                        append("Privacy Policy")
                    }
                    pop()
                }

                ClickableText(
                    text = termsOfUseAnnotatedText,
                    onClick = { offset ->
                        termsOfUseAnnotatedText.getStringAnnotations(
                            tag = "TermsOfUse",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            navController.navigate(AuthenticationScreen.SignUp.route)
                        }
                        termsOfUseAnnotatedText.getStringAnnotations(
                            tag = "PrivacyPolicy",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let { annotation ->
                            navController.navigate(AuthenticationScreen.SignUp.route)
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    if (loading) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            CircularProgressIndicator(
                color = Pink1,
                strokeWidth = 16.dp,
                modifier = Modifier
                    .size(150.dp)
            )
        }
    }
}

