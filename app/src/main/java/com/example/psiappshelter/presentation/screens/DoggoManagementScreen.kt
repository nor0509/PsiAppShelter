package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psiappshelter.ui.theme.PinkLight
import com.example.psiappshelter.ui.theme.PurpleGrey40
import com.example.psiappshelter.ui.theme.PurpleGrey80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoggoManagementScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onDoggoClick: (String) -> Unit,
    doggoViewModel: DoggoViewModel

) {
    var doggoName by remember { mutableStateOf("") }
    val doggoList = doggoViewModel.doggoList
    var showAddDialog by remember { mutableStateOf(false) }
    var tempDoggoName by remember { mutableStateOf("") }
    var doggoExists by remember{mutableStateOf(false)}
    var doggoSearch by remember{mutableStateOf(false)}
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = (PurpleGrey80.copy(alpha = 0.15f)),
                ),
                title = {
                    Text("Doggos")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onSettingsClick()}
                    ){
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Cofnij"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onProfileClick() }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                OutlinedTextField(
                    value = doggoName,
                    onValueChange = {
                        doggoName = it
                        doggoSearch = false
                    },
                    label = { Text("Poszukaj lub dodaj pieska \uD83D\uDC15") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(2f),
                    isError = doggoExists
                )

                Row(
                    modifier = Modifier
                        .weight(0.8f),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        modifier = Modifier.size(34.dp),
                        onClick = {
                            if (doggoList.isNotEmpty()) {
                                doggoSearch = true
                            }
                        },
                        enabled = doggoName.isNotEmpty()
                    ) {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "Wyszukaj pieska"
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                    IconButton(
                        onClick = {
                            if (doggoList.none{it.name == doggoName} ) {
                                tempDoggoName = doggoName
                                showAddDialog = true
                                doggoName = ""
                            }
                            else {
                                doggoExists = true
                            }
                        },
                        modifier = Modifier.size(34.dp),
                        enabled = doggoName.isNotEmpty()
                    ) {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Dodaj pieska",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(30.dp)
            ){
                if (doggoExists){
                    Text(
                        "Piesek o takim imieniu znajduje się już na liście!",
                        fontSize = 10.sp,
                        color = Color.Red)
                }
                else{
                    Spacer(modifier = Modifier)
                }
            }
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

                    Text(": ${doggoList.filter{ it.isFavorite }.size}")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Top

            ) {
                if (!doggoSearch) {
                    items(
                        doggoList.sortedBy { !it.isFavorite }

                    ) { doggo ->
                        DoggoCard(
                            doggo = doggo,
                            onFavoriteClick = {
                                doggoViewModel.toggleFavorite(doggo)
                            },
                            onDeleteClick = {
                                doggoViewModel.removeDoggo(doggo)
                            },
                            onCardClick = {
                                onDoggoClick(doggo.id)
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 14.dp, end = 24.dp),
                            color = PurpleGrey40.copy(alpha = 0.3f),
                            thickness = 2.dp
                        )
                    }
                }
                else {

                    items(
                        doggoList.filter { it.name.startsWith(doggoName) },
                    ) { doggo ->
                        DoggoCard(
                            doggo = doggo,
                            onFavoriteClick = {
                                doggoViewModel.toggleFavorite(doggo)
                            },
                            onDeleteClick = {
                                doggoViewModel.removeDoggo(doggo)
                            },
                            onCardClick = {
                                onDoggoClick(doggo.id)
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .size(4.dp),
                            color = PinkLight,
                            thickness = 1.dp
                        )
                    }
                }

            }
        }


        if (showAddDialog) {
            doggoExists = false
            AddDoggoDialog(
                doggoName = tempDoggoName,
                onAddDoggo = { breed, age ->
                    doggoList.add(
                        Doggo(
                            name = tempDoggoName,
                            breed = breed,
                            age = age,
                            isFavorite = false
                        )
                    )
                    showAddDialog = false
                },
                onDismiss = { showAddDialog = false }
            )
        }
    }
}

