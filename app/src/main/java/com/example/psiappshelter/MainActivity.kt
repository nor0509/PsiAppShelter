package com.example.psiappshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.layout.VerticalAlignmentLine


data class Doggo(
    val name: String,
    val breed: String,
    val age: Int,
    var isFavorite: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DoggoManagementScreen()
            }
        }
    }
}

@Composable
fun DoggoManagementScreen(modifier: Modifier = Modifier) {
    var doggoName by remember { mutableStateOf("") }
    val doggoList = remember { mutableStateListOf<Doggo>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var tempDoggoName by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
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
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { /* TODO: Search Action */ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
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
                        modifier = Modifier
                            .size(48.dp)
                            .padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Dodaj pieska",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "\uD83D\uDC36: ${doggoList.size}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)

                )
                Text(
                    text = ": ${doggoList.count { it.isFavorite }}",
                    style = MaterialTheme.typography.bodyLarge
                )
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

@Composable
fun AddDoggoDialog(
    doggoName: String,
    onAddDoggo: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Dodaj pieska") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Imię: $doggoName")
                OutlinedTextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("Rasa") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Wiek") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val parsedAge = age.toIntOrNull() ?: 0
                    if (breed.isNotEmpty() && parsedAge > 0) {
                        onAddDoggo(breed, parsedAge)
                    }
                }
            ) {
                Text("Dodaj")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Anuluj")
            }
        }
    )
}

@Composable
fun DoggoCard(
    doggo: Doggo,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "\uD83D\uDC15",
                fontSize = 20.sp
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 12.dp)

        ){
            Text(
                text = doggo.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${doggo.breed}, ${doggo.age} years old",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Row(
            modifier = Modifier
                .weight(0.3f),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = if (doggo.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder  ,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )

            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(24.dp)

            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.delete_icon
                    ),
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )

            }
        }

    }
}


//@Composable
//fun FavoriteToggleIcon() {
//    var isFavorite by remember { mutableStateOf(false) }
//
//    IconButton(onClick = { isFavorite = !isFavorite }) {
//        Icon(
//            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
//            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
//        )
//    }
//}



@Preview(showBackground = true)
@Composable
fun DoggoManagementScreenPreview() {
    MaterialTheme {
        DoggoManagementScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DoggoCardPreview() {
    MaterialTheme {
        DoggoCard(
            doggo = Doggo(
                name = "Pan Punpernikiel",
                breed = "Jack Russel",
                age = 3,
                isFavorite = false
            ),
            onFavoriteClick = {},
            onDeleteClick = {}
        )
    }
}