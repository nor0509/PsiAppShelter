package com.example.psiappshelter.data.di

import android.content.Context
import androidx.room.Room
import com.example.psiappshelter.data.DogRepository
import com.example.psiappshelter.data.local.database.DogDatabase
import com.example.psiappshelter.data.local.database.DogEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): DogDatabase {
        return Room.databaseBuilder(
            context,
            DogDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideDogDao(appDatabase: DogDatabase): DogEntityDao {
        return appDatabase.dogDao()
    }

    @Provides
    fun provideDogRepository(dogEntityDao: DogEntityDao): DogRepository {
        return DogRepository(dogEntityDao)
    }
}