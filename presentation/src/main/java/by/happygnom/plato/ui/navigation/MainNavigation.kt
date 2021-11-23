package by.happygnom.plato.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import by.happygnom.plato.R
import by.happygnom.plato.ui.screens.profile.ProfileScreen
import by.happygnom.plato.ui.screens.projects.ProjectsScreen
import by.happygnom.plato.ui.screens.routes.RoutesScreen
import by.happygnom.plato.ui.screens.settings.SettingsScreen


sealed class Screen(val route: String) {
    object Main : Screen("main")
}

sealed class MainScreen(
    val route: String,
    @StringRes val labelStringId: Int,
    @DrawableRes val iconDrawableId: Int
) {
    object Routes : MainScreen("main/routes", R.string.routes, R.drawable.ic_routes)
    object Projects : MainScreen("main/projects", R.string.projects, R.drawable.ic_project)
    object Profile : MainScreen("main/profile", R.string.profile, R.drawable.ic_user)
    object Settings : MainScreen("main/settings", R.string.settings, R.drawable.ic_settings)
}

@Composable
fun MainNavigation(
    navController: NavHostController, graph: NavGraphBuilder.() -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        graph()
    }
}

fun NavGraphBuilder.addMainGraph(
    navController: NavController,
) {
    navigation(route = Screen.Main.route, startDestination = MainScreen.Routes.route) {
        composable(MainScreen.Routes.route) {
            RoutesScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(MainScreen.Projects.route) {
            ProjectsScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(MainScreen.Profile.route) {
            ProfileScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(MainScreen.Settings.route) {
            SettingsScreen(viewModel = hiltViewModel(), navController = navController)
        }
    }
}
