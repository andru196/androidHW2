package com.example.poke.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.poke.domain.entity.Pokemon
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FavoritesDaoImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : FavoritesDao {

    companion object {
        private const val FAVORITES_KEY = "FAVORITES_KEY"
    }

    private var pokemons: List<Pokemon>
        get() {
            return sharedPreferences.getString(FAVORITES_KEY, null)?.let {
                Json.decodeFromString(it)
            } ?: emptyList()
        }
        set(value) {
            sharedPreferences.edit {
                putString(FAVORITES_KEY, Json.encodeToString(value))
            }
        }

    private var state: List<Pokemon> = pokemons

    override suspend fun add(pokemon: Pokemon) {
        pokemons += pokemon
        state += pokemon

    }

    override suspend fun delete(pokemon: Pokemon) {
        pokemons -= pokemon
        state -= pokemon

    }

    override suspend fun isInFavorites(pokemon: Pokemon): Boolean =
        pokemons.contains(pokemon)

    override fun getFavorites(): List<Pokemon> = state

    override fun getCount(): Int {
        return state.size
    }
}