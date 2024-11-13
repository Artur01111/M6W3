package com.example.m6w3

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Modifier

sealed class Screen(val route: String, val title: String) {
    object Characters : Screen("characters", "Characters")
    object Episodes : Screen("episodes", "Episodes")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar(title = { Text("Rick and Morty API") }) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Characters.route) { CharacterScreen() }
            composable(Screen.Episodes.route) { EpisodeScreen() }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController.currentDestination?.route
        val items = listOf(Screen.Characters, Screen.Episodes)
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}