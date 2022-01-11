package com.example.poke.di

import com.example.poke.data.local.FavoritesDao
import com.example.poke.data.local.FavoritesDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesModule {

    @Binds
    @Singleton
    fun bindFavoritesDao(favoritesDaoImpl: FavoritesDaoImpl): FavoritesDao
}