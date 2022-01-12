package com.example.poke.data.database

import com.example.poke.data.database.open.OpenItemDao
import com.example.poke.data.database.open.OpenItemEntity
import com.example.poke.data.database.pokemon.PokemonEntity
import com.example.poke.data.database.pokemon.PokemonEntityDao
import com.example.poke.data.database.search.SearchItemDao
import com.example.poke.data.database.search.SearchItemEntity
import com.example.poke.domain.DatabaseRepository
import com.example.poke.domain.entity.Pokemon
import com.example.poke.domain.entity.Search
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val searchItemDao: SearchItemDao,
    private val openItemDao: OpenItemDao,
    private val pokemonEntityDao: PokemonEntityDao
) : DatabaseRepository {
    override suspend fun addOpen(pokemonId: Int) {
        openItemDao.insert(OpenItemEntity(0, pokemonId = pokemonId.toLong()))
    }

    override suspend fun getOpens() =
        openItemDao.getAll()
            .map { x-> x.pokemonId.toInt() }

    override suspend fun getOpenedPokemon(): List<Pokemon> =
        openItemDao.getAll().map { x -> pokemonEntityDao.getById(x.pokemonId.toInt())!!.toPokemon() }


    override suspend fun addSearch(search: Search) = searchItemDao.insert(SearchItemEntity(
        0, search.searchText, search.results
                                        .map { x -> x.id.toString() }
                                        .joinToString(" ")
    ))

    override suspend fun getSearches() = searchItemDao.getAll().map { x ->
        Search(
            x.text,
            x.result
                .split(" ")
                .map { y -> y.toInt() }
                .map { y -> pokemonEntityDao.getById(y)!!.toPokemon() }
                .toList()
        )
    }

    override suspend fun addPokemon(pokemon: Pokemon) = pokemonEntityDao
        .insert(pokemon.toPokemonEntity())

    override suspend fun getPokemonByName(name:String) =
        pokemonEntityDao.getByName(name)
            .map { x -> x.toPokemon() }

    override suspend fun getPokemonById(id:Int) =
        pokemonEntityDao.getById(id)?.toPokemon()

    private fun  PokemonEntity.toPokemon() = Pokemon(
        id = id,
        name = name,
        color = color,
        height = height,
        weight = weight,
        is_default = is_default,
        baseExp = generation
    )

    private fun  Pokemon.toPokemonEntity() = PokemonEntity(
        id = id,
        name = name,
        color = color,
        height = height,
        weight = weight,
        is_default = is_default,
        generation = baseExp
    )
}