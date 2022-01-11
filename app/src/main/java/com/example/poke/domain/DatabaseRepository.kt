package com.example.poke.domain

import com.example.poke.domain.entity.Pokemon
import com.example.poke.domain.entity.Search

interface DatabaseRepository {
    suspend fun addOpen(pokemonId: Int)
    suspend fun getOpens() : List<Int>
    suspend fun getOpenedPokemon() : List<Pokemon>
    suspend fun addSearch(search: Search)
    suspend fun getSearches() : List<Search>
    suspend fun addPokemon(pokemon: Pokemon)
    suspend fun getPokemonByName(name:String) : List<Pokemon>
    suspend fun getPokemonById(id:Int) : Pokemon?
}