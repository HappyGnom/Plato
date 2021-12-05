package by.happygnom.plato.ui.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.LabeledLogo
import by.happygnom.plato.ui.elements.button.StrokeImageButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextField
import by.happygnom.plato.ui.navigation.AuthenticationScreen
import by.happygnom.plato.ui.screens.auth.main.AuthViewModel
import by.happygnom.plato.ui.theme.Grey1
import by.happygnom.plato.ui.theme.Grey2
import by.happygnom.plato.ui.theme.Pink2
import by.happygnom.plato.ui.theme.Teal1
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    onSignIn: () -> Unit
) {

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
                text = "Welcome back",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.h2
            )

            InputTextField(
                text = "",
                hint = "Enter your email",
                onValueChange = {},
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth(1f),
                singleLine = true
            )

            InputTextField(
                text = "",
                hint = "Enter your password",
                onValueChange = {},
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(1f),
                singleLine = true,
                enableVisibilityToggle = true
            )

            TealFilledButton(
                text = "Login",
                onClick = { navController.navigate(AuthenticationScreen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 32.dp)
            )

            val signUpAnnotatedText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.finlandica_regular, FontWeight.Normal))
                    )
                ) {
                    append("No plato account yet?   ")
                }
                pushStringAnnotation(
                    tag = "SignUp",
                    annotation = "SignUp"
                )
                withStyle(
                    style = SpanStyle(
                        color = Teal1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.finlandica_regular))
                    )
                ) {
                    append("SignUp")
                }
                pop()
            }

            ClickableText(
                text = signUpAnnotatedText,
                onClick = { offset ->
                    signUpAnnotatedText.getStringAnnotations(
                        tag = "SignUp",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let { annotation ->
                        navController.navigate(AuthenticationScreen.SignUp.route)
                    }
                },
                modifier = Modifier.padding(top = 32.dp)
            )

            val forgotPasswordAnnotatedText = buildAnnotatedString {
                pushStringAnnotation(tag = "Forgot", annotation = "Forgot")
                withStyle(
                    style = SpanStyle(
                        color = Pink2,
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily(Font(R.font.finlandica_regular, FontWeight.Normal))
                    )
                ) {
                    append("Forgot password?")
                }
                pop()
            }

            ClickableText(
                text = forgotPasswordAnnotatedText,
                onClick = { offset ->
                    forgotPasswordAnnotatedText.getStringAnnotations(
                        tag = "Forgot",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let { annotation ->
                        navController.navigate(AuthenticationScreen.SignUp.route)
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
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
                text = "Sign in with Google",
                drawableId = R.drawable.ic_google,
                onClick = {
                    val currentUser = viewModel.currentUser
                    if (currentUser == null) {
                        onSignIn()
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
                        fontFamily = FontFamily(Font(R.font.finlandica_regular, FontWeight.Normal))
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
                        fontFamily = FontFamily(Font(R.font.finlandica_regular, FontWeight.Normal)),
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
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}

