package com.example.poke.presentation.pokemonDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke.data.local.FavoritesDao
import com.example.poke.domain.PokeRepository
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.SingleLiveEvent
import com.example.poke.presentation.common.launchWithErrorHandler

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject


class PokemonDetailViewModel @AssistedInject constructor(
    @Assisted private val pokemon: Pokemon,
    private val pokeRepository: PokeRepository,
    private val favoritesDao: FavoritesDao

    ) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(pokemon: Pokemon): PokemonDetailViewModel

    }

    fun onBackPressed() {
        _backAction.value = Unit
    }

    private val _pokemonState = MutableLiveData(pokemon)
    val pokemonState: LiveData<Pokemon> = _pokemonState

    private val _backAction = SingleLiveEvent<Unit>()
    val backAction: LiveData<Unit> = _backAction

    private val _favoritesState = MutableLiveData<Boolean>()
    val favoritesState: LiveData<Boolean> = _favoritesState

    init {
        viewModelScope.launch {
            _favoritesState.value = favoritesDao.isInFavorites(pokemon)
        }
    }

    fun onFavoritesClicked() {
        viewModelScope.launchWithErrorHandler {
            val isInFavorites = favoritesDao.isInFavorites(pokemon)
            _favoritesState.value = !isInFavorites

            if (isInFavorites) {
                favoritesDao.delete(pokemon)
            } else {
                favoritesDao.add(pokemon)
            }
        }
    }


}

class PokemonDetailViewModelFactory @Inject constructor(
    private  val pokeRepository: PokeRepository,
    private val favoritesDao: FavoritesDao
) {
    fun create(pokemon: Pokemon): PokemonDetailViewModel =
        PokemonDetailViewModel(pokemon, pokeRepository, favoritesDao)
}


