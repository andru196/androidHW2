package com.example.poke.data.database.open

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.poke.data.database.search.SearchItemEntity

@Dao
interface OpenItemDao {
    @Insert
    suspend fun insert(openItemEntity: OpenItemEntity)
    @Query("SELECT * FROM open_item_entity")
    suspend fun getAll(): List<OpenItemEntity>
}