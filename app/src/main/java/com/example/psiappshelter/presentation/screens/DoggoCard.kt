package com.example.psiappshelter.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psiappshelter.Doggo
import com.example.psiappshelter.R

@Composable
fun DoggoCard(
    doggo: Doggo,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable(onClick = onCardClick),
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