package com.example.poke.data.database.open

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "open_item_entity")
class OpenItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pokemonId: Long
)