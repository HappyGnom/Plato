package by.happygnom.plato.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import by.happygnom.plato.ui.screens.auth.getStarted.GetStartedScreen
import by.happygnom.plato.ui.screens.auth.login.LoginScreen
import by.happygnom.plato.ui.screens.auth.main.AuthScreen
import by.happygnom.plato.ui.screens.auth.signup.SignUpScreen
import by.happygnom.plato.ui.screens.auth.signup_details.SignUpDetailsScreen

sealed class AuthScreen(val route: String) {
    object Auth : Screen("auth")
}

sealed class AuthenticationScreen(
    val route: String,
) {
    object Main : AuthenticationScreen("auth/main")
    object SignUp : AuthenticationScreen("auth/signup")
    object SignUpDetails : AuthenticationScreen("auth/signupDetails")
    object Login : AuthenticationScreen("auth/login")
    object GetStarted : AuthenticationScreen("auth/getStarted")
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    graph: NavGraphBuilder.() -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Auth.route,
        modifier = modifier
    ) {
        graph()
    }
}

fun NavGraphBuilder.addAuthGraph(
    navController: NavController
) {
    navigation(route = AuthScreen.Auth.route, startDestination = AuthenticationScreen.Main.route) {
        composable(AuthenticationScreen.Main.route) {
            AuthScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(AuthenticationScreen.SignUp.route) {
            SignUpScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(AuthenticationScreen.Login.route) {
            LoginScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(AuthenticationScreen.GetStarted.route) {
            GetStartedScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(AuthenticationScreen.SignUpDetails.route) {
            SignUpDetailsScreen(viewModel = hiltViewModel(), navController = navController)
        }
    }
}
