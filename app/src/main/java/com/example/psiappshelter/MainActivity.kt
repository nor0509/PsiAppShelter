package com.example.psiappshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.psiappshelter.presentation.screens.SettingsScreen
import com.example.psiappshelter.presentation.screens.DoggoManagementScreen
import java.util.UUID

data class Doggo(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val breed: String,
    val age: Int,
    var isFavorite: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navigationController = rememberNavController()
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
                            }
                        )
                    }
                    composable(
                        route = "doggo_details/{doggoId}",
                        arguments = listOf(navArgument("doggoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val doggoId = backStackEntry.arguments?.getString("doggoId") ?: ""
                        DoggoDetailsScreen(doggoId)
                    }
                    composable("settings") {
                        SettingsScreen()
                    }
                }
            }
        }
    }
}




