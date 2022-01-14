package com.example.poke.presentation.pokemonSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.domain.DatabaseRepository
import com.example.poke.domain.PokeRepository
import com.example.poke.domain.entity.Pokemon
import com.example.poke.domain.entity.Search
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val pokeRepository: PokeRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
//    private var searchJob: Job? = null


    private val _openDetailAction = SingleLiveEvent<Pokemon>()
    val openDetailAction: LiveData<Pokemon> = _openDetailAction

    private val _screenState = MutableLiveData<PokemonSearchState>(
        PokemonSearchState.Loading())
    val screenState: LiveData<PokemonSearchState> = _screenState

    private val _searchErrorState = MutableLiveData<SearchState>(SearchState.VALID)
    val searchErrorState: LiveData<SearchState> = _searchErrorState

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState


    init {
        viewModelScope.launchWithErrorHandler(block =  {
            _screenState.value = PokemonSearchState.Success(emptyList())
        }, onError = {
            _screenState.value = PokemonSearchState.Error(it)
        })
    }

    fun onPokemonClicked(pokemon: Pokemon) {
        _openDetailAction.value = pokemon
        viewModelScope.launchWithErrorHandler {
            val pokemons = databaseRepository.addOpen(pokemonId = pokemon.id)
        }
    }

    fun pokemonSearch(text: String) {
        var hasError = false
        if (text.isBlank()) {
//            return
            _searchErrorState.value = SearchState.EMPTY
            var hasError = true
        }
        if (hasError) return
        _searchErrorState.value = SearchState.VALID
        _loadingState.value = true

        viewModelScope.launchWithErrorHandler(block = {
            withContext(Dispatchers.IO) {
                val pokemons = pokeRepository.searchPokemons(text)
                _screenState.postValue(PokemonSearchState.Success(pokemons))
                saveResult(text, pokemons)
            }
        }, onError = {
            _screenState.value = PokemonSearchState.Error(it)
        },
        )
    }

    fun searchTextChanged(text: String) {
        val lastState = (_screenState.value as? PokemonSearchState.Success)?.pokemons
//            ?: (_screenState.value as? PokemonSearchState.Editing)?.pokemons
//        _screenState.value = PokemonSearchState.Loading()
        viewModelScope.launchWithErrorHandler {
            val pokemons = databaseRepository.getPokemonByName(text)
            if (pokemons.isNotEmpty())
                _screenState.postValue(PokemonSearchState.Editing(pokemons))

            else if (lastState != null)
                _screenState.postValue(PokemonSearchState.Editing(lastState))
        }
    }


    private suspend fun saveResult(text: String, pokemons: List<Pokemon>) {
        pokemons.forEach { pokemon ->
            if (databaseRepository.getPokemonById(pokemon.id) == null)
            databaseRepository.addPokemon(pokemon)
        }
        databaseRepository.addSearch(Search(text, pokemons))
    }
}


sealed class PokemonSearchState {
    class Loading() : PokemonSearchState()
    class Success(val pokemons: List<Pokemon>) : PokemonSearchState()
    class Editing(val pokemons: List<Pokemon>) : PokemonSearchState()
    class Error(val throwable: Throwable) : PokemonSearchState()
}

enum class SearchState {
    EMPTY, VALID

}
