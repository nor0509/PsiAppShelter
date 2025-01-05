package com.example.psiappshelter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoggoDetailsScreen(doggoId: String) {
    val doggoList = remember {
        listOf(
            Doggo(name = "Pan Punpernikiel", breed = "Jack Russel", age = 3, isFavorite = false),
            Doggo(name = "Pani Piesek", breed = "Labrador", age = 5, isFavorite = true)
        )
    }

    val doggo = doggoList.find { it.id == doggoId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły Psa") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (doggo != null) {
                Text(text = "Imię: ${doggo.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Rasa: ${doggo.breed}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Wiek: ${doggo.age} lat", style = MaterialTheme.typography.bodyLarge)
            } else {
                Text(
                    text = "Nie znaleziono psa o ID: $doggoId",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
