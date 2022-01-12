package com.example.poke.di

import android.content.Context
import com.example.poke.data.database.DataBaseRepositoryImpl
import com.example.poke.data.database.MainDatabase
import com.example.poke.data.database.open.OpenItemDao
import com.example.poke.data.database.pokemon.PokemonEntityDao
import com.example.poke.data.database.search.SearchItemDao
import com.example.poke.domain.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseModule {
    companion object {
        private var db: MainDatabase? = null

        private fun get(context: Context): MainDatabase {
            return  db?: MainDatabase.create(context).also { db = it }
        }


        @Provides
        @Singleton
        fun provideSearchItemDao(@ApplicationContext context: Context): SearchItemDao =
            get(context).searchItemDao

        @Provides
        @Singleton
        fun providePokemonEntityDao(@ApplicationContext context: Context): PokemonEntityDao =
            get(context).pokemonEntityDao

        @Provides
        @Singleton
        fun provideOpenItemDao(@ApplicationContext context: Context): OpenItemDao =
            get(context).openItemDao
    }

    @Binds
    @Singleton
    abstract fun getDatabaseRepository(
        databaseRepository: DataBaseRepositoryImpl
    ): DatabaseRepository
}