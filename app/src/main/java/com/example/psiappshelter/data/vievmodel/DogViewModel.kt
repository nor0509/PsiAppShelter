package com.example.psiappshelter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psiappshelter.data.DogRepository
import com.example.psiappshelter.data.local.entity.DogEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import fetchRandomDogImageUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {

    private val _dogs = MutableStateFlow<List<DogEntity>>(emptyList())
    val dogs: StateFlow<List<DogEntity>> get() = _dogs

    init {

        viewModelScope.launch {
            repository.readAllData.collect { dogList ->
                _dogs.value = dogList
            }
        }
    }


    fun addDog(name: String, breed: String, age: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val dogProfileUrl = fetchRandomDogImageUrl() ?: ""
                    val dog = DogEntity(
                        name = name,
                        breed = breed,
                        age = age,
                        profileImageUrl = dogProfileUrl
                    )
                    repository.addDog(dog)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun removeDog(dog: DogEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteDog(dog.uid)
            }
        }
    }

    fun toggleFavorite(dog: DogEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.toggleFavorite(dog.uid)
            }
        }

    }

    fun getProfileImageUrlForDog(dog: DogEntity): String {
        return dog.profileImageUrl
    }
}