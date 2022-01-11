package com.example.poke.data.database.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_item_entity")
class SearchItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val text: String,
    val result: String
)