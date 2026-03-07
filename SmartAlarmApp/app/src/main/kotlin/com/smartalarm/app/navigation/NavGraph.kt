package com.smartalarm.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smartalarm.app.screens.ActiveAlarmScreen
import com.smartalarm.app.screens.CreateAlarmScreen
import com.smartalarm.app.screens.EditAlarmScreen
import com.smartalarm.app.screens.HomeScreen
import com.smartalarm.app.screens.LoginScreen
import com.smartalarm.app.screens.RegisterScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("create") {
            CreateAlarmScreen(navController)
        }
        composable(
            "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "1"
            EditAlarmScreen(navController, id)
        }
        composable(
            "active/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "1"
            ActiveAlarmScreen(navController, id)
        }
    }
}
