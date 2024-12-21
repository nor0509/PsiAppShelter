package com.example.psiappshelter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.res.painterResource


data class Doggo(val name: String, var isFavorite: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DoggoManagementScreen()
            }
        }
    }
}

@Composable
fun DoggoManagementScreen() {
    var doggoName by remember { mutableStateOf(TextFieldValue("")) }
    var doggos by remember { mutableStateOf(listOf<Doggo>()) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val dogNames = remember { mutableSetOf<String>() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = doggoName,
                onValueChange = {
                    doggoName = it
                    errorText = null
                },
                label = null,
                modifier = Modifier
                    .weight(1f)
                    .background(if (errorText != null) Color(0xFFFFCDD2) else Color.Transparent),
                singleLine = true,
                isError = errorText != null
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    val trimmedName = doggoName.text.trim()
                    if (trimmedName.isNotBlank()) {
                    }
                },
                enabled = doggoName.text.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Szukaj psa",
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = {
                    val trimmedName = doggoName.text.trim()
                    if (trimmedName.isNotBlank()) {
                        if (!dogNames.contains(trimmedName)) {
                            dogNames.add(trimmedName)
                            doggos = listOf(Doggo(trimmedName, false)) + doggos
                            doggoName = TextFieldValue("")
                        } else {
                            errorText = "Piesek o tej nazwie już istnieje!"
                        }
                    }
                },
                enabled = doggoName.text.isNotBlank() && errorText == null
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj psa",
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        errorText?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "🐶 ${doggos.size}", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(16.dp))
            if (doggos.any { it.isFavorite }) {
                Text(text = "❤️ ${doggos.count { it.isFavorite }}", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(doggos) { doggo ->
                DoggoCard(
                    doggo = doggo,
                    onFavoriteClick = {
                        doggos = doggos.map { currentDoggo ->
                            if (currentDoggo.name == doggo.name) {
                                currentDoggo.copy(isFavorite = !currentDoggo.isFavorite)
                            } else {
                                currentDoggo
                            }
                        }.sortedByDescending { it.isFavorite }
                    },
                    onDeleteClick = {
                        doggos = doggos.filter { it.name != doggo.name }
                        dogNames.remove(doggo.name)
                    }
                )
            }
        }
    }
}

@Composable
fun DoggoCard(doggo: Doggo, onFavoriteClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFFBB86FC))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🐕",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = doggo.name, fontSize = 18.sp, modifier = Modifier.weight(1f))

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    painter = painterResource(id = R.drawable.fav_icon),
                    contentDescription = "Ulubiony piesek",
                    tint = if (doggo.isFavorite) Color.Red else Color.Gray
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "Usuń pieska",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoggoManagementScreenPreview() {
    MaterialTheme {
        DoggoManagementScreen()
    }
}
