package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.psiappshelter.Doggo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoggoManagementScreen(
    modifier: Modifier = Modifier,
    onDoggoClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    var doggoName by remember { mutableStateOf("") }
    val doggoList = remember { mutableStateListOf<Doggo>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var tempDoggoName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doggo Shelter") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ustawienia"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = doggoName,
                    onValueChange = { doggoName = it },
                    label = { Text("Wpisz imię psa") },
                    singleLine = true,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { /* TODO: Implement search functionality */ },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Wyszukaj pieska"
                    )
                }
                IconButton(
                    onClick = {
                        if (doggoName.isNotEmpty()) {
                            tempDoggoName = doggoName
                            showAddDialog = true
                            doggoName = ""
                        }
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Dodaj pieska",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(doggoList) { doggo ->
                    DoggoCard(
                        doggo = doggo,
                        onFavoriteClick = {
                            val index = doggoList.indexOf(doggo)
                            if (index != -1) {
                                doggoList[index] = doggo.copy(isFavorite = !doggo.isFavorite)
                            }
                        },
                        onDeleteClick = {
                            doggoList.remove(doggo)
                        },
                        onCardClick = {
                            onDoggoClick(doggo.id)
                        }
                    )
                }
            }
        }


        if (showAddDialog) {
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

@Preview(showBackground = true)
@Composable
fun DoggoManagementScreenPreview() {
    MaterialTheme {
        DoggoManagementScreen(
            onDoggoClick = {},
            onSettingsClick = {}
        )
    }
}