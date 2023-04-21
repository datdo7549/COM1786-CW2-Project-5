package com.example.comp1786cw2project2.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comp1786cw2project2.local.model.Url

@Database(entities = [Url::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun urlDao(): UrlDao
}