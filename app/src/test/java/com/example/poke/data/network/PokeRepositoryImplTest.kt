package com.example.poke.data.network

import com.google.android.gms.common.internal.Asserts
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient

class PokeRepositoryImplTest : TestCase() {


    fun testSearch() = runBlocking {
        val searchText = "ba"

        val rep = PokeRepositoryImpl(PokeApiClient())
        val rez = rep.searchPokemons(searchText)
        assert(!rez.isEmpty())

    }
}
