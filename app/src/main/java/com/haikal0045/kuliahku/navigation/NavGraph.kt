package com.haikal0045.kuliahku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.haikal0045.kuliahku.ui.screen.DetailScreen
import com.haikal0045.kuliahku.ui.screen.MainScreen
import com.haikal0045.kuliahku.ui.screen.RecycleBinScreen
import com.haikal0045.kuliahku.ui.screen.ThemeScreen

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController = navController)
        }

        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController = navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_TUGAS) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(KEY_ID_TUGAS)
            DetailScreen(
                navController = navController,
                id = id
            )
        }

        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(navController = navController)
        }

        composable(route = Screen.Tema.route) {
            ThemeScreen(navController = navController)
        }
    }
}