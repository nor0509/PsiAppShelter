package com.example.psiappshelter.ui.components.doggomanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoggoSearchBar(
    doggoName: String,
    onDoggoNameChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onAddClick: () -> Unit,
    doggoExists: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = doggoName,
                onValueChange = onDoggoNameChange,
                label = { Text("Poszukaj lub dodaj pieska 🐕") },
                singleLine = true,
                modifier = Modifier.weight(2f),
                isError = doggoExists
            )

            Row(
                modifier = Modifier.weight(0.8f).align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    modifier = Modifier.size(34.dp),
                    onClick = onSearchClick,
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
                    onClick = onAddClick,
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
        if (doggoExists) {
            Text(
                "Piesek o takim imieniu znajduje się już na liście!",
                fontSize = 10.sp,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
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