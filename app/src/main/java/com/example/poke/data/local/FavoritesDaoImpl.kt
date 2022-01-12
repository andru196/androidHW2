package com.example.poke.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.poke.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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

    private var films: List<Pokemon>
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

    private val state: Flow<List<Pokemon>> = MutableStateFlow(films)

    override fun add(film: Pokemon) {
        films += film
    }

    override fun delete(film: Pokemon) {
        films -= film
    }

    override fun isInFavorites(film: Pokemon): Boolean =
        films.contains(film)

    override fun getFavorites(): Flow<List<Pokemon>> = state

    override fun getCount(): Flow<Int> {
        return state.map { it.size }
    }
}