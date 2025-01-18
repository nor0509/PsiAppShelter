package com.example.psiappshelter.data

import com.example.psiappshelter.data.local.database.DogEntityDao
import com.example.psiappshelter.data.local.entity.DogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogEntityDao: DogEntityDao) {

    val readAllData: Flow<List<DogEntity>> = dogEntityDao.readAllData()


    suspend fun addDog(dog: DogEntity) {
        dogEntityDao.addDog(dog)
    }

    suspend fun toggleFavorite(id: Int) {
        dogEntityDao.toggleFavorite(id)
    }


    suspend fun deleteDog(id: Int) {
        dogEntityDao.deleteDog(id)
    }



    fun getDogById(id: Int): Flow<DogEntity?> {
        return dogEntityDao.getDogById(id)
    }

    fun getAllDogs(): Flow<List<DogEntity>> {
        return dogEntityDao.readAllData()
    }

}