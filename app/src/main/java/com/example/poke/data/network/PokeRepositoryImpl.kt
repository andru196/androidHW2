package com.example.poke.data.network

import com.example.poke.domain.PokeRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.ApiResource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import java.util.Collections.synchronizedList
import javax.inject.Inject

final class PokeRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApiClient
) : PokeRepository {

    private var _allPokemons: List<NamedApiResource>? = null

    override suspend fun searchPokemons(search: String): List<Pokemon> {
        val ids = getAllPokemonsList()
            .filter { x -> x.name.lowercase().contains(search) }
            .map { x -> x.id }
            .toIntArray()
        return getPokemons(ids = ids)
    }

    override suspend fun getPokemonById(id: Int): Pokemon = coroutineScope {
        pokeApi.getPokemon(id)
    }

    suspend fun getPokemons(vararg ids:Int): List<Pokemon> {
        val rez =  arrayListOf<Deferred<Pokemon>>()
        coroutineScope {
            ids.forEach { id ->
                 rez.add(async {  pokeApi.getPokemon(id) })
            }
        }
        return rez.map { x -> x.await() }
    }

    suspend fun getAllPokemonsList() = coroutineScope {
        _allPokemons ?: pokeApi.getPokemonList(0, 100000).results.also {
            _allPokemons = it }
    }

}