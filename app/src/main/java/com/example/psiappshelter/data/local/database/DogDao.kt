package com.example.psiappshelter.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.psiappshelter.data.local.entity.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogEntityDao {

    @Query("SELECT * FROM dogs")
    fun readAllData(): Flow<List<DogEntity>>

    @Insert
    suspend fun addDog(dog: DogEntity)

    @Query("DELETE FROM dogs WHERE uid = :id")
    suspend fun deleteDog(id: Int)

    @Query("UPDATE dogs SET isFav = NOT isFav WHERE uid = :id")
    suspend fun toggleFavorite(id: Int)

    @Query("SELECT * FROM dogs WHERE uid = :id LIMIT 1")
    fun getDogById(id: Int): Flow<DogEntity?>

}