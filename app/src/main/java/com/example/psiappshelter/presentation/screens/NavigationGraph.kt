package com.example.psiappshelter.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavigationGraph(
    navController: NavHostController,
    doggoViewModel: DoggoViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "doggo_management"
    ) {
        composable("doggo_management") {
            DoggoManagementScreen(
                onDoggoClick = { doggoId ->
                    navController.navigate("doggo_details/$doggoId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onProfileClick = {
                    navController.navigate("profile")
                },
                doggoViewModel = doggoViewModel
            )
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable(
            route = "doggo_details/{doggoId}",
            arguments = listOf(navArgument("doggoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doggoId = backStackEntry.arguments?.getString("doggoId")
            DoggoDetailsScreen(
                navController = navController,
                doggoId = doggoId,
                doggoViewModel = doggoViewModel
            )
        }
    }
}