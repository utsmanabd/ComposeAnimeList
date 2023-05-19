package com.everybodv.jetanimelist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.everybodv.jetanimelist.R
import com.everybodv.jetanimelist.ui.navigation.NavigationItem
import com.everybodv.jetanimelist.ui.navigation.NavigationScreen
import com.everybodv.jetanimelist.ui.screen.DetailScreen
import com.everybodv.jetanimelist.ui.screen.HomeScreen
import com.everybodv.jetanimelist.ui.screen.ProfileScreen
import com.everybodv.jetanimelist.ui.screen.WatchListScreen
import com.everybodv.jetanimelist.ui.theme.JetAnimeListTheme

@Composable
fun JetAnimeListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != NavigationScreen.Detail.route)
                BottomBar(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationScreen.Home.route) {
                HomeScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(NavigationScreen.Detail.createRoute(animeId))
                    }
                )
            }
            composable(NavigationScreen.WatchList.route) { WatchListScreen() }
            composable(NavigationScreen.Profile.route) { ProfileScreen() }
            composable(
                route = NavigationScreen.Detail.route,
                arguments = listOf(navArgument("animeId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("animeId") ?: -1L
                DetailScreen(
                    animeId = id,
                    navigateBack = { navController.navigateUp() },
                    addToWatchList = {
                        navController.popBackStack()
                        navController.navigate(NavigationScreen.WatchList.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home),
                icon = Icons.Default.Home,
                screen = NavigationScreen.Home
            ),NavigationItem(
                title = stringResource(id = R.string.watchlist),
                icon = Icons.Default.List,
                screen = NavigationScreen.WatchList
            ),NavigationItem(
                title = stringResource(id = R.string.profile),
                icon = Icons.Default.AccountCircle,
                screen = NavigationScreen.Profile
            ),
        )
        BottomNavigation {
            navigationItem.map { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetAnimeListAppPreview() {
    JetAnimeListTheme {
        JetAnimeListApp()
    }
}