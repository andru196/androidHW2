package com.example.poke.presentation.pokemonFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.domain.PokeRepository
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import javax.inject.Inject

@HiltViewModel
class PokemonFavoriteViewModel @Inject constructor(
    private val pokeRepository: PokeRepository
) : ViewModel() {

    private val _openDetailAction = SingleLiveEvent<Pokemon>()
    val openDetailAction: LiveData<Pokemon> = _openDetailAction


    private val _screenState = MutableLiveData<PokemonFavoriteState>(PokemonFavoriteState.Loading())
    val screenState: LiveData<PokemonFavoriteState> = _screenState

    init {
        viewModelScope.launchWithErrorHandler(block = {
            _screenState.value = PokemonFavoriteState.Success(emptyList())
        }, onError = {
            _screenState.value = PokemonFavoriteState.Error(it)
        })
    }

    fun onPokemonClicked(pokemon: Pokemon) {
        _openDetailAction.value = pokemon
    }

    sealed class PokemonFavoriteState {
        class Loading() : PokemonFavoriteState()
        class Success(val pokemons: List<Pokemon>) : PokemonFavoriteState()
        class Error(val throwable: Throwable) : PokemonFavoriteState()
    }
}