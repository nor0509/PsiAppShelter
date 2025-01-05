package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.psiappshelter.ui.theme.PurpleGrey40

@Composable
fun DoggoList(
    doggoList: List<Doggo>,
    filterQuery: String,
    onFavoriteClick: (Doggo) -> Unit,
    onDeleteClick: (Doggo) -> Unit,
    onCardClick: (String) -> Unit,
    doggoSearch: Boolean
) {
    val filteredDoggoList = if (!doggoSearch) {
        doggoList.sortedBy { !it.isFavorite }
    } else {
        doggoList.filter { it.name.startsWith(filterQuery, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Top
    ) {
        items(filteredDoggoList) { doggo ->
            DoggoCard(
                doggo = doggo,
                onFavoriteClick = { onFavoriteClick(doggo) },
                onDeleteClick = { onDeleteClick(doggo) },
                onCardClick = { onCardClick(doggo.id) }
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 14.dp, end = 24.dp),
                color = PurpleGrey40.copy(alpha = 0.3f),
                thickness = 2.dp
            )
        }
    }
}