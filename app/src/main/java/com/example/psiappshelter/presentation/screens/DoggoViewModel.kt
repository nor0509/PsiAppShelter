package com.example.psiappshelter.presentation.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

data class Doggo(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val breed: String,
    val age: Int,
    var isFavorite: Boolean
)

class DoggoViewModel : ViewModel() {
    val doggoList = mutableStateListOf(
        Doggo(name = "Rex", breed = "German Shepherd", age = 3, isFavorite = true),
        Doggo(name = "Bella", breed = "Labrador", age = 2, isFavorite = false),
        Doggo(name = "Charlie", breed = "Beagle", age = 4, isFavorite = true)
    )

    fun addDoggo(doggo: Doggo) {
        doggoList.add(doggo)
    }

    fun removeDoggo(doggo: Doggo) {
        doggoList.remove(doggo)
    }

    fun toggleFavorite(doggo: Doggo) {
        val index = doggoList.indexOf(doggo)
        if (index != -1) {
            doggoList[index] = doggo.copy(isFavorite = !doggo.isFavorite)
        }
    }
}
