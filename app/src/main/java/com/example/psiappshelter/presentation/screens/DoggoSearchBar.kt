package com.example.psiappshelter.presentation.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

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

@Preview(showBackground = true)
@Composable
fun DoggoSearchBarPreview() {
    val doggoName = remember { mutableStateOf("") }
    val doggoExists = remember { mutableStateOf(false) }

    DoggoSearchBar(
        doggoName = doggoName.value,
        onDoggoNameChange = { doggoName.value = it },
        onSearchClick = {},
        onAddClick = {},
        doggoExists = doggoExists.value
    )
}