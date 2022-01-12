package com.example.poke.data.network

import com.example.poke.domain.PokeRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import java.util.*
import javax.inject.Inject

final class PokeRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApiClient
) : PokeRepository {

    private var _allPokemons: List<NamedApiResource>? = null

    override suspend fun searchPokemons(search: String): List<com.example.poke.domain.entity.Pokemon> {
        val ids = getAllPokemonsList()
            .filter { x -> x.name.lowercase().contains(search) }
            .map { x -> x.id }
            .toIntArray()
        return getPokemons(ids = ids).map { x -> x.toPokemon() }
    }

    override suspend fun getPokemonById(id: Int) = coroutineScope {
        pokeApi.getPokemon(id).toPokemon()
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


    private fun Pokemon.toPokemon() = com.example.poke.domain.entity.Pokemon(
        id = id,
        name = name,
        color = (0..0xffffff).random(),
        height = height.toDouble(),
        weight = weight.toDouble(),
        is_default = isDefault,
        baseExp = baseExperience
    )

    private fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

}