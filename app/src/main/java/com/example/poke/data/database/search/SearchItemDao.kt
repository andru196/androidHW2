package com.example.poke.data.database.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchItemDao {
    @Insert
    suspend fun insert(searchItemEntity: SearchItemEntity)
    @Query("SELECT * FROM search_item_entity")
    suspend fun getAll(): List<SearchItemEntity>
}