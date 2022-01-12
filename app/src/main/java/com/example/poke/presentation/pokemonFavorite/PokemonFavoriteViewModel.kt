package com.example.poke.presentation.pokemonFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.domain.DatabaseRepository
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonFavoriteViewModel @Inject constructor(
    //private val pokeRepository: PokeRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val MAX_FAV  = 20

    private val _openDetailAction = SingleLiveEvent<Pokemon>()
    val openDetailAction: LiveData<Pokemon> = _openDetailAction


    private val _screenStateOpened = MutableLiveData<PokemonFavoriteState>(
        PokemonFavoriteState.Loading()
    )
    val screenStateOpened: LiveData<PokemonFavoriteState> = _screenStateOpened

    private val _screenStateSearched = MutableLiveData<PokemonFavoriteState>(
        PokemonFavoriteState.Loading()
    )
    val screenStateSearched: LiveData<PokemonFavoriteState> = _screenStateSearched


    init {
        _screenStateOpened.value = PokemonFavoriteState.Loading()
        _screenStateSearched.value = PokemonFavoriteState.Loading()

        viewModelScope.launchWithErrorHandler(block = {
            val pokemons = getMostPopular()
            _screenStateOpened.value = PokemonFavoriteState.Success(pokemons)
        }, onError = {
            _screenStateOpened.value = PokemonFavoriteState.Error(it)
        })
        viewModelScope.launchWithErrorHandler(block = {
            val pokemons = getMostSearched()
            _screenStateSearched.value = PokemonFavoriteState.Success(pokemons)
        }, onError = {
            _screenStateOpened.value = PokemonFavoriteState.Error(it)
        })
    }

    fun onPokemonClicked(pokemon: com.example.poke.domain.entity.Pokemon) {
        _openDetailAction.value = pokemon
        viewModelScope.launchWithErrorHandler {
            val pokemons = databaseRepository.addOpen(pokemonId = pokemon.id)
        }
    }


    private suspend fun getMostPopular(): List<Pokemon> {
        val grouped = databaseRepository.getOpenedPokemon()
            .groupBy { x -> x.id }
        val result = mutableMapOf<Pokemon, Int>()
        for (g in grouped)
            result[g.value.last()] = g.value.size
        val realRes = ArrayList<Pokemon>()
        result.values.sortedBy{x->x}.forEach { z ->
            if (realRes.size < MAX_FAV)
                realRes.addAll(result.filter { x -> x.value == z }.keys)
        }
        return realRes.reversed()
    }

    private suspend fun getMostSearched(): List<Pokemon> {
        val grouped = databaseRepository.getSearches()
            .flatMap {  x-> x.results }
            .groupBy { x -> x.id }
        val result = mutableMapOf<Pokemon, Int>()
        for (g in grouped)
            result[g.value.last()] = g.value.size
        val realRes = ArrayList<Pokemon>()
        result.values.sortedBy{x->x}.forEach { z ->
            if (realRes.size < MAX_FAV)
                realRes.addAll(result.filter { x -> x.value == z }.keys)
        }
        return realRes.reversed()
    }

    sealed class PokemonFavoriteState {
        class Loading() : PokemonFavoriteState()
        class Success(val pokemons: List<Pokemon>) : PokemonFavoriteState()
        class Error(val throwable: Throwable) : PokemonFavoriteState()
    }
}