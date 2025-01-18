package com.example.psiappshelter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.psiappshelter.ui.screens.ProfileScreen
import com.example.psiappshelter.ui.screens.SettingsScreen
import com.example.psiappshelter.ui.screens.doggomanagement.DogDetailsScreen
import com.example.psiappshelter.ui.screens.doggomanagement.DogManagementScreen
import com.example.psiappshelter.viewmodel.DogViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    doggoViewModel: DogViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "doggo_management"
    ) {
        composable("doggo_management") {
            DogManagementScreen(
                onDogClick = { doggoId ->
                    navController.navigate("doggo_details/$doggoId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onProfileClick = {
                    navController.navigate("profile")
                },
                dogViewModel = doggoViewModel
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
            DogDetailsScreen(
                navController = navController,
                dogId = doggoId,
                dogViewModel = doggoViewModel
            )
        }
    }
}