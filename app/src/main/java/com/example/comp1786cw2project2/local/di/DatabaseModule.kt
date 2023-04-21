package com.example.comp1786cw2project2.local.di

import android.content.Context
import androidx.room.Room
import com.example.comp1786cw2project2.local.database.AppDatabase
import com.example.comp1786cw2project2.local.database.UrlDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "data"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideUrlDao(appDatabase: AppDatabase) = appDatabase.urlDao()
}