package com.example.psiappshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.psiappshelter.presentation.screens.NavigationGraph
import com.example.psiappshelter.presentation.screens.DoggoViewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val doggoViewModel: DoggoViewModel = viewModel()
                NavigationGraph(navController = navController, doggoViewModel = doggoViewModel)
            }
        }

    }
}




