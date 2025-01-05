package com.example.psiappshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.psiappshelter.presentation.screens.DoggoDetailsScreen
import com.example.psiappshelter.presentation.screens.DoggoManagementScreen
import com.example.psiappshelter.presentation.screens.DoggoViewModel
import com.example.psiappshelter.presentation.screens.ProfileScreen
import com.example.psiappshelter.presentation.screens.SettingsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navigationController = rememberNavController()
                val doggoViewModel: DoggoViewModel = viewModel()
                NavHost(
                    navController = navigationController,
                    startDestination = "doggo_management"
                ) {
                    composable("doggo_management") {
                        DoggoManagementScreen(
                            onDoggoClick = { doggoId ->
                                navigationController.navigate("doggo_details/$doggoId")
                            },
                            onSettingsClick = {
                                navigationController.navigate("settings")
                            },
                            onProfileClick = {
                                navigationController.navigate("profile")
                            },
                            doggoViewModel = doggoViewModel

                        )
                    }

                    composable("settings") {
                        SettingsScreen(navController = navigationController)
                    }
                    composable("profile") {
                        ProfileScreen(navController = navigationController)
                    }
                    composable(
                        route = "doggo_details/{doggoId}",
                        arguments = listOf(navArgument("doggoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val doggoId = backStackEntry.arguments?.getString("doggoId")
                        DoggoDetailsScreen(
                            navController = navigationController,
                            doggoId = doggoId,
                            doggoViewModel = doggoViewModel
                        )
                    }
                }
            }
        }
    }
}




