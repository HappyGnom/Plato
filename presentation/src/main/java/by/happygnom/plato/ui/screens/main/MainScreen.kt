package by.happygnom.plato.ui.screens.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import by.happygnom.plato.ui.navigation.MainNavigation
import by.happygnom.plato.ui.navigation.MainScreen
import by.happygnom.plato.ui.navigation.addMainGraph
import by.happygnom.plato.ui.theme.*
import by.happygnom.plato.ui.theme.Grey1

@Composable
fun MainScreen(viewModel: MainViewModel, onSignOut: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentDestinationRoute = currentDestination?.route ?: ""

    val bottomNavigationItems = listOf(
        MainScreen.Routes,
        MainScreen.Stats,
        MainScreen.News,
        MainScreen.User
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = White,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, Grey3)
            ) {
                bottomNavigationItems.forEach { screen ->
                    BottomNavBarItem(
                        screen = screen,
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = { bottomNavBarNavigateTo(navController, screen.route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        MainNavigation(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            graph = {
                addMainGraph(navController, onSignOut)
            })
    }
}

@Composable
fun RowScope.BottomNavBarItem(screen: MainScreen, selected: Boolean, onClick: () -> Unit) {
    val textColor = if (selected) Teal1 else Grey2

    BottomNavigationItem(
        icon = {
            Icon(
                painterResource(id = screen.iconDrawableId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        label = {
            Text(
                text = stringResource(screen.labelStringId),
                style = MaterialTheme.typography.body2.copy(textColor)
            )
        },
        selected = selected,
        onClick = onClick,
        selectedContentColor = Teal1,
        unselectedContentColor = Grey2,
        modifier = Modifier.then(
            Modifier.weight(if (selected) 1.5f else 1f)
        )
    )
}

fun bottomNavBarNavigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true

        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
