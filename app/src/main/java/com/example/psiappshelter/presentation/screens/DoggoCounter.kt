package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DoggoCounter(doggoList: List<Doggo>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("\uD83D\uDC36: ${doggoList.size}")

        if (doggoList.any { it.isFavorite }) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 6.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = "Dodaj pieska",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(": ${doggoList.filter { it.isFavorite }.size}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoggoCounterPreview() {
    val doggoViewModel = DoggoViewModel()
    MaterialTheme {
        DoggoCounter(doggoList = doggoViewModel.doggoList)
    }
}

