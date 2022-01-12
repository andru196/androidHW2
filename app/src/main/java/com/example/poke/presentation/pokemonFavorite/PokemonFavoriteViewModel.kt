package com.example.poke.presentation.pokemonFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.domain.DatabaseRepository
import com.example.poke.domain.PokeRepository
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import javax.inject.Inject

@HiltViewModel
class PokemonFavoriteViewModel @Inject constructor(
    private val pokeRepository: PokeRepository,
    private val databaseRepository: DatabaseRepository

) : ViewModel() {


    private val _openDetailAction = SingleLiveEvent<com.example.poke.domain.entity.Pokemon>()
    val openDetailAction: LiveData<com.example.poke.domain.entity.Pokemon> = _openDetailAction


    private val _screenState = MutableLiveData<PokemonFavoriteFragment.PokemonFavoriteState>(
        PokemonFavoriteFragment.PokemonFavoriteState.Loading()
    )
    val screenState: LiveData<PokemonFavoriteFragment.PokemonFavoriteState> = _screenState

    init {
        viewModelScope.launchWithErrorHandler(block = {
            _screenState.value = PokemonFavoriteFragment.PokemonFavoriteState.Success(emptyList())
        }, onError = {
            _screenState.value = PokemonFavoriteFragment.PokemonFavoriteState.Error(it)
        })
    }

    fun onPokemonClicked(pokemon: com.example.poke.domain.entity.Pokemon) {
        _openDetailAction.value = pokemon
        viewModelScope.launchWithErrorHandler {
            val pokemons = databaseRepository.addOpen(pokemonId = pokemon.id)
        }


    }
    sealed class PokemonFavoriteState {
        class Loading() : PokemonFavoriteState()
        class Success(val pokemons: List<com.example.poke.domain.entity.Pokemon>) : PokemonFavoriteState()
        class Error(val throwable: Throwable) : PokemonFavoriteState()
    }
}