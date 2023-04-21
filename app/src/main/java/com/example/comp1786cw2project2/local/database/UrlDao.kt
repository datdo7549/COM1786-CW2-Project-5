package com.example.comp1786cw2project2.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comp1786cw2project2.local.model.Url

@Dao
interface UrlDao {
    @Insert
    fun addUrl(url: Url)

    @Query("SELECT * FROM url")
    fun getListUrl(): List<Url>
}