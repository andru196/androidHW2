package com.example.poke.domain

import me.sargunvohra.lib.pokekotlin.model.Pokemon

interface PokeRepository {

    suspend fun searchPokemons(search:String): List<Pokemon>

    suspend fun getPokemonById(id:Int): Pokemon

}