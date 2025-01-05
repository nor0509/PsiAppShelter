package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
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
            DoggoSearchBar(
                doggoName = doggoName,
                onDoggoNameChange = {
                    doggoName = it
                    doggoSearch = false
                    doggoExists = false
                },
                onSearchClick = {
                    if (doggoList.isNotEmpty()) {
                        doggoSearch = true
                    }
                },
                onAddClick = {
                    if (doggoList.none { it.name == doggoName }) {
                        tempDoggoName = doggoName
                        showAddDialog = true
                        doggoName = ""
                    } else {
                        doggoExists = true
                    }
                },
                doggoExists = doggoExists
            )
            DoggoCounter(doggoList)
            DoggoList(
                doggoList = doggoList,
                filterQuery = doggoName,
                onFavoriteClick = { doggoViewModel.toggleFavorite(it) },
                onDeleteClick = { doggoViewModel.removeDoggo(it) },
                onCardClick = onDoggoClick,
                doggoSearch = doggoSearch
            )
        }


        if (showAddDialog) {
            AddDoggoDialog(
                doggoName = tempDoggoName,
                onAddDoggo = { breed, age ->
                    doggoViewModel.addDoggo(tempDoggoName, breed, age)
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
    val doggoViewModel = DoggoViewModel()
    MaterialTheme {
        DoggoManagementScreen(
            onSettingsClick = {},
            onProfileClick = {},
            onDoggoClick = {},
            doggoViewModel = doggoViewModel
        )
    }
}
