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
}