package com.example.poke.di

import com.example.poke.data.network.PokeRepositoryImpl
import com.example.poke.domain.PokeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        @Singleton
        fun provideFilmApi(): PokeApiClient =
            PokeApiClient()
    }



    @Binds
    @Singleton
    abstract fun getGeoRepository(
        pokeRepositoryImpl: PokeRepositoryImpl
    ): PokeRepository

}