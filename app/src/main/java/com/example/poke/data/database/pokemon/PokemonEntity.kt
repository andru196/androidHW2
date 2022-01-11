package com.example.poke.data.database.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_entity")
class PokemonEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val color: Int,
    val height: Double,
    val weight: Double,
    val is_default: Boolean,
    val generation: Int
)