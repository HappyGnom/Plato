package by.happygnom.plato.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.happygnom.plato.R
import by.happygnom.plato.ui.screens.auth.getStarted.GetStartedScreen
import by.happygnom.plato.ui.screens.auth.login.LoginScreen
import by.happygnom.plato.ui.screens.auth.main.AuthScreen
import by.happygnom.plato.ui.screens.auth.signup.SignUpScreen
import by.happygnom.plato.ui.screens.news.NewsScreen
import by.happygnom.plato.ui.screens.routes.add_comment.AddCommentScreen
import by.happygnom.plato.ui.screens.routes.comments.CommentsScreen
import by.happygnom.plato.ui.screens.routes.details.RouteDetailsScreen
import by.happygnom.plato.ui.screens.routes.filter.RoutesFilterScreen
import by.happygnom.plato.ui.screens.routes.list.RoutesListScreen
import by.happygnom.plato.ui.screens.routes.route_editor.RouteEditorScreen
import by.happygnom.plato.ui.screens.stats.StatsScreen
import by.happygnom.plato.ui.screens.user.UserScreen
import java.util.*
import kotlin.reflect.KFunction2

sealed class Screen(val route: String) {
    object Authorization : Screen("authorization")
    object Main : Screen("main")
}

sealed class MainScreen(
    val route: String,
    @StringRes val labelStringId: Int,
    @DrawableRes val iconDrawableId: Int,
) {
    object Routes : MainScreen("main/routes", R.string.routes, R.drawable.ic_routes)
    object Stats : MainScreen("main/stats", R.string.stats, R.drawable.ic_stats)
    object News : MainScreen("main/news", R.string.news, R.drawable.ic_news)
    object User : MainScreen("main/user", R.string.user, R.drawable.ic_user)
}

sealed class RoutesScreen(
    val route: String,
) {
    object List : RoutesScreen("main/routes/list")
    object Filter : RoutesScreen("main/routes/list/filters")
    object RouteDetails : RoutesScreen("main/routes/list/{route_id}") {
        fun createRoute(routeId: String) = "main/routes/list/$routeId"
    }

    object Comments : RoutesScreen("main/routes/list/{route_id}/comments") {
        fun createRoute(routeId: String) = "main/routes/list/$routeId/comments"
    }

    object AddComment : RoutesScreen("main/routes/list/{route_id}/add-comment") {
        fun createRoute(routeId: String) = "main/routes/list/$routeId/add-comment"
    }

    object Editor : RoutesScreen("main/routes/list/edit?existing_route_id={existing_route_id}") {
        fun createRoute(existingRouteId: String?) =
            "main/routes/list/edit?existing_route_id=$existingRouteId"
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController,
    graph: NavGraphBuilder.() -> Unit,
    modifier: Modifier = Modifier,
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
    onSignOut: () -> Unit
) {
    navigation(route = Screen.Main.route, startDestination = MainScreen.Routes.route) {
        addRoutesGraph(navController)

        composable(MainScreen.Stats.route) {
            StatsScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(MainScreen.News.route) {
            NewsScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(MainScreen.User.route) {
            UserScreen(viewModel = hiltViewModel(), navController = navController, onSignOut = onSignOut)
        }
    }
}

fun NavGraphBuilder.addRoutesGraph(
    navController: NavController
) {
    navigation(route = MainScreen.Routes.route, startDestination = RoutesScreen.List.route) {
        composable(RoutesScreen.List.route) {
            RoutesListScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(RoutesScreen.Filter.route) {
            RoutesFilterScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(RoutesScreen.RouteDetails.route) {
            val routeId = it.arguments?.getString("route_id") ?: return@composable

            RouteDetailsScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                routeId = routeId
            )
        }

        composable(RoutesScreen.Comments.route) {
            val routeId = it.arguments?.getString("route_id") ?: return@composable

            CommentsScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                routeId = routeId
            )
        }

        composable(RoutesScreen.AddComment.route) {
            val routeId = it.arguments?.getString("route_id") ?: return@composable

            AddCommentScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                routeId = routeId
            )
        }

        composable(
            RoutesScreen.Editor.route,
            arguments = listOf(navArgument("existing_route_id") { nullable = true })
        ) {
            val existingRouteId = it.arguments?.getString("existing_route_id")

            RouteEditorScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                existingRouteId = existingRouteId
            )
        }
    }
}
