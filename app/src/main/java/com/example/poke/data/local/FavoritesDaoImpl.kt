package com.example.poke.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.poke.domain.entity.Film
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

    private var films: List<Film>
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

    private val state: Flow<List<Film>> = MutableStateFlow(films)

    override fun add(film: Film) {
        films += film
    }

    override fun delete(film: Film) {
        films -= film
    }

    override fun isInFavorites(film: Film): Boolean =
        films.contains(film)

    override fun getFavorites(): Flow<List<Film>> = state

    override fun getCount(): Flow<Int> {
        return state.map { it.size }
    }
}