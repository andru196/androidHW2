package com.example.poke.presentation.pokemonSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.domain.PokeRepository
import com.example.poke.domain.entity.Film
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val pokeRepository: PokeRepository
) : ViewModel() {

    private val _openDetailAction = SingleLiveEvent<Pokemon>()
    val openDetailAction: LiveData<Pokemon> = _openDetailAction

    private val _screenState = MutableLiveData<PokemonSearchState>(
        PokemonSearchState.Loading())

    val screenState: LiveData<PokemonSearchState> = _screenState


    init {
        viewModelScope.launchWithErrorHandler(block =  {
            _screenState.value = PokemonSearchState.Success(emptyList())
        }, onError = {
            _screenState.value = PokemonSearchState.Error(it)
        })
    }

    fun onPokemonClicked(pokemon: Pokemon) {
        _openDetailAction.value = pokemon
    }

    fun pokemonSearch(text:String) {
        _screenState.value = PokemonSearchState.Loading()
        viewModelScope.launchWithErrorHandler(block = {
            withContext(Dispatchers.IO) {
                val pokemons = pokeRepository.searchPokemons(text)
                _screenState.postValue(PokemonSearchState.Success(pokemons))
            }
        }, onError = {
            _screenState.value = PokemonSearchState.Error(it)
        })
    }

}

sealed class PokemonSearchState {
    class Loading(): PokemonSearchState()
    class Success(val pokemons: List<Pokemon>): PokemonSearchState()
    class Error(val throwable: Throwable) : PokemonSearchState()
}
