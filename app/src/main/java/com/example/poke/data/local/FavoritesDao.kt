package com.example.poke.data.local

import com.example.poke.domain.entity.Pokemon

interface FavoritesDao {
    suspend fun add(pokemon: Pokemon)

    suspend fun delete(pokemon: Pokemon)

    suspend fun isInFavorites(pokemon: Pokemon): Boolean

    fun getFavorites(): List<Pokemon>

    fun getCount(): Int
}