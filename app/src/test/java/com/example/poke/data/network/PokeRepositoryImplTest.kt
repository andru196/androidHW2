package com.example.poke.data.network

import com.google.android.gms.common.internal.Asserts
import junit.framework.TestCase
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient

class PokeRepositoryImplTest : TestCase() {

    fun testGet1() {
        val rep = PokeRepositoryImpl(PokeApiClient())
        val l = rep.get1()
        Asserts.checkNull(3)
    }

    fun testGet3() {
        val rep = PokeRepositoryImpl(PokeApiClient())
        val l = rep.getAllPokemonsList()
        Asserts.checkNull(3)
    }


    fun testSearch() {
        val searchText = "ba"

        val rep = PokeRepositoryImpl(PokeApiClient())
        val l = rep.getAllPokemonsList()
        val needs = l.filter { x -> x.name.contains(searchText) }
            .map { x -> x.id }
            .toIntArray()
        val rez = rep.getPokemons(ids = needs)
    }
}