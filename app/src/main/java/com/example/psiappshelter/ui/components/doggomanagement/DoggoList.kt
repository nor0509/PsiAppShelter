package com.example.psiappshelter.ui.components.doggomanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.psiappshelter.data.local.entity.DogEntity
import com.example.psiappshelter.ui.theme.PurpleGrey40

@Composable
fun DoggoListScreen(
    doggoList: List<DogEntity>,
    filterQuery: String,
    onFavoriteClick: (DogEntity) -> Unit,
    onDeleteClick: (DogEntity) -> Unit,
    onCardClick: (String) -> Unit,
    doggoSearch: Boolean
) {

    val listToDisplay = if (doggoSearch) {

        doggoList.filter { it.name.startsWith(filterQuery, ignoreCase = true) }
    } else {

        doggoList.sortedBy { !it.isFav }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Top
    ) {
        items(listToDisplay) { doggo ->

            DoggoCard(
                doggo = doggo,
                onFavoriteClick = { onFavoriteClick(doggo) },
                onDeleteClick = { onDeleteClick(doggo) },
                onCardClick = { onCardClick(doggo.uid.toString()) },
                modifier = Modifier,
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 14.dp, end = 24.dp),
                color = PurpleGrey40.copy(alpha = 0.3f),
                thickness = 2.dp
            )
        }
    }
}