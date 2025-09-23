package klimov.example.aad.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import klimov.example.aad.R
import klimov.example.aad.ui.screen.NewsScreen
import klimov.example.aad.ui.screen.SettingScreen

@Composable
fun BottomBarNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.News.route
    ) {
        composable(route = BottomBarScreen.News.route) {
            NewsScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingScreen()
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    navDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(R.string.navbar_icon)
            )
        },
        label = {
            Text(text = screen.title)
        },
        selected = navDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(BottomBarScreen.News, BottomBarScreen.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navDestination = currentDestination,
                navController = navController
            )
        }
    }
}

