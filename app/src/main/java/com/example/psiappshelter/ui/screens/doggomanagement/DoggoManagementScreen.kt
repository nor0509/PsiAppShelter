package com.example.psiappshelter.ui.screens.doggomanagement

import android.util.Log
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.psiappshelter.ui.components.doggomanagement.AddDoggoDialog
import com.example.psiappshelter.ui.components.doggomanagement.DoggoCounter
import com.example.psiappshelter.ui.components.doggomanagement.DoggoListScreen
import com.example.psiappshelter.ui.components.doggomanagement.DoggoSearchBar
import com.example.psiappshelter.ui.theme.PurpleGrey80
import com.example.psiappshelter.viewmodel.DogViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogManagementScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onDogClick: (String) -> Unit,
    dogViewModel: DogViewModel = hiltViewModel()
) {
    var dogName by remember { mutableStateOf("") }
    val dogList by dogViewModel.dogs.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var dogExists by remember { mutableStateOf(false) }
    var dogSearch by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    fun handleAdd() {
        if (dogList.none { it.name == dogName }) {
            showAddDialog = true
            dogExists = false
        } else {
            dogExists = true
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80.copy(alpha = 0.15f),
                ),
                title = {
                    Text("Doggos")
                },
                navigationIcon = {
                    IconButton(onClick = { onSettingsClick() }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onProfileClick() }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
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
                doggoName = dogName,
                onDoggoNameChange = {
                    dogName = it
                    dogExists = false
                    if (dogName.isBlank()) {
                        dogSearch = false
                    }
                },
                onSearchClick = { dogSearch = dogName.isNotEmpty() },
                onAddClick = { handleAdd() },
                doggoExists = dogExists
            )
            DoggoCounter(dogListFlow = dogViewModel.dogs)
            DoggoListScreen(
                doggoList = dogList,
                filterQuery = dogName,
                onFavoriteClick = {
                    Log.d("DoggoCard", "Favorite icon clicked for dog: ${it.name}")
                    dogViewModel.toggleFavorite(it)
                },
                onDeleteClick = { dogViewModel.removeDog(it) },
                onCardClick = onDogClick,
                doggoSearch = dogSearch
            )
        }
    }

    if (showAddDialog) {
        AddDoggoDialog(
            doggoName = dogName,
            onAddDoggo = { breed, age ->
                dogViewModel.addDog(dogName, breed, age)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}

