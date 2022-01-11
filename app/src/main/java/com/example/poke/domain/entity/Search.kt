package com.example.poke.domain.entity

data class Search(
    val searchText: String,
    val results: List<Pokemon>
)