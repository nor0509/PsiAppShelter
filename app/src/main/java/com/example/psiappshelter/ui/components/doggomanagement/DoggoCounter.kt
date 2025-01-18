package com.example.psiappshelter.ui.components.doggomanagement

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.psiappshelter.data.local.entity.DogEntity
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DoggoCounter(dogListFlow: StateFlow<List<DogEntity>>) {
    val doggoList by dogListFlow.collectAsState(initial = emptyList())

    val totalDogs = doggoList.size
    val favoriteDogs = doggoList.count { it.isFav }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("\uD83D\uDC36: $totalDogs")

        if (favoriteDogs > 0) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 6.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = "Ulubione pieski",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(": $favoriteDogs")
        }
    }
}