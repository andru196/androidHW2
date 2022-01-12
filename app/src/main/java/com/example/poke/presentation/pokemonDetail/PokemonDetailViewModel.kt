package com.example.poke.presentation.pokemonDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poke.domain.PokeRepository
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.SingleLiveEvent

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject


class PokemonDetailViewModel @AssistedInject constructor(
    @Assisted private val pokemon: Pokemon,
    private val pokeRepository: PokeRepository
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


}


class PokemonDetailViewModelFactory @Inject constructor(
    private val pokeRepository: PokeRepository
) {
    fun create(pokemon: Pokemon): PokemonDetailViewModel = PokemonDetailViewModel(pokemon, pokeRepository)
}