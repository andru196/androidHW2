package com.example.poke.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.poke.data.database.open.OpenItemDao
import com.example.poke.data.database.open.OpenItemEntity
import com.example.poke.data.database.pokemon.PokemonEntity
import com.example.poke.data.database.pokemon.PokemonEntityDao
import com.example.poke.data.database.search.SearchItemDao
import com.example.poke.data.database.search.SearchItemEntity

@Database(entities = [SearchItemEntity::class,
                     OpenItemEntity::class,
                     PokemonEntity::class],
    version = 1,
    exportSchema = true)
abstract class MainDatabase: RoomDatabase() {
    abstract val searchItemDao: SearchItemDao
    abstract val openItemDao: OpenItemDao
    abstract val pokemonEntityDao: PokemonEntityDao

    companion object{
        fun create(context:Context) : MainDatabase = Room
            .databaseBuilder(context, MainDatabase::class.java, "main_database")
            .build()
    }
}