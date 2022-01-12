package com.example.poke.data.database.pokemon

import androidx.room.*

@Dao
interface PokemonEntityDao {
    @Insert
    suspend fun insert(pokemonEntity: PokemonEntity)

    @Delete
    suspend fun delete(pokemonEntity: PokemonEntity)

    @Update
    suspend fun update(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon_entity")
    suspend fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_entity WHERE id == :searchId")
    suspend fun getById(searchId: Int): PokemonEntity?

    @Query("SELECT * FROM pokemon_entity WHERE name LIKE '%' || :search || '%'")
    suspend fun getByName(search: String?): List<PokemonEntity>
}