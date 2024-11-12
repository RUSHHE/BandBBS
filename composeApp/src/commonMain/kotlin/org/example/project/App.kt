package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.project.component.DiscoverScreen
import org.example.project.component.HomeScreen
import org.example.project.component.ProfileScreen
import org.example.project.component.ResourceScreen
import org.example.project.theme.ThemeManager
import org.example.project.theme.ThemeType
import org.jetbrains.compose.ui.tooling.preview.Preview
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.Box as MiuixBox
import top.yukonga.miuix.kmp.basic.Scaffold as MiuixScaffold

@Composable
@Preview
fun App() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    if (ThemeManager.currentTheme == ThemeType.Miuix) {
        MiuixTheme(
            colors = if (isSystemInDarkTheme()) top.yukonga.miuix.kmp.theme.darkColorScheme() else top.yukonga.miuix.kmp.theme.lightColorScheme()
        ) {
            val navController: NavHostController = rememberNavController()
            MiuixScaffold(
                topBar = { MiuixTopAppBar() },
                bottomBar = { MiuixNavigationBar(navController) },
            ) { paddingValues ->
                MiuixBox(Modifier.padding(paddingValues)) {
                    NavHostContainer(navController)
                }
            }
        }
    } else {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) androidx.compose.material3.darkColorScheme() else androidx.compose.material3.lightColorScheme()
        ) {
            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    AppDestinations.entries.forEach {
                        item(
                            icon = {
                                Icon(
                                    it.icon,
                                    contentDescription = it.contentDescription
                                )
                            },
                            label = { Text(it.label) },
                            selected = it == currentDestination,
                            onClick = { currentDestination = it }
                        )
                    }
                }
            ) {
                when (currentDestination) {
                    AppDestinations.HOME -> HomeScreen()
                    AppDestinations.RESOURCE -> ResourceScreen()
                    AppDestinations.DISCOVER -> DiscoverScreen()
                    AppDestinations.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("resource") {
            ResourceScreen()
        }
        composable("discover") {
            DiscoverScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}

@Composable
fun MiuixTopAppBar() {
    // TODO: 添加Miuix风格导航栏
    TopAppBar(
        title = "BandBBS",
    )
}

@Composable
fun MiuixNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        items = AppDestinations.entries.map { item ->
            NavigationItem(
                label = item.label,
                icon = item.icon,
            )
        },
        selected = AppDestinations.entries.map { it.route }.indexOf(currentRoute),
        onClick = {
            navController.navigate(AppDestinations.entries[it].route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}