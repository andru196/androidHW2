package com.example.poke.data.local

import com.example.poke.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface FavoritesDao {
    fun add(film: Pokemon)

    fun delete(film: Pokemon)

    fun isInFavorites(film: Pokemon): Boolean

    fun getFavorites(): Flow<List<Pokemon>>

    fun getCount(): Flow<Int>
}