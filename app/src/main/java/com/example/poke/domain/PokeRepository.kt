package com.example.poke.domain

import com.example.poke.domain.entity.Pokemon

interface PokeRepository {

    suspend fun searchPokemons(search:String): List<Pokemon>

    suspend fun getPokemonById(id:Int): Pokemon

}