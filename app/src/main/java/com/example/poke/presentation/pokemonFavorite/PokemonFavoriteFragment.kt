package com.example.poke.presentation.pokemonFavorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.poke.R
import com.example.poke.databinding.TopPokeScreenBinding
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.BaseFragment
import com.example.poke.presentation.common.navigate
import com.example.poke.presentation.pokemonDetail.PokemonDetailFragment
import com.example.poke.presentation.pokemonFavorite.PokemonFavoriteViewModel.PokemonFavoriteState
import com.example.poke.presentation.pokemonSearch.PokemonSearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFavoriteFragment : BaseFragment(R.layout.top_poke_screen) {

    private val viewBinding by viewBinding(TopPokeScreenBinding::bind)
    private val viewModel by viewModels<PokemonFavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonHorizontalAdapter = PokemonHorizontalAdapter(viewModel::onPokemonClicked)
        with(viewBinding.favoritesPokeView) {
            adapter = pokemonHorizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        val pokemonHorizontalAdapter2 = PokemonHorizontalAdapter(viewModel::onPokemonClicked)
        with(viewBinding.mostPopularPokeView) {
            adapter = pokemonHorizontalAdapter2
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }


        viewModel.screenStateOpened.observe(viewLifecycleOwner) {
            when (it) {
                is PokemonFavoriteState.Error -> {
                    viewBinding.loadFavoritePokeError.isVisible = true
                    viewBinding.loadFavoritePokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = false

                }
                is PokemonFavoriteState.Loading -> {
                    viewBinding.loadFavoritePokeError.isVisible = false
                    viewBinding.loadFavoritePokeProgress.isVisible = true
                    viewBinding.favoritesPokeView.isVisible = false
                }
                is PokemonFavoriteState.Success -> {
                    Log.d("pokeCount", it.pokemons.size.toString())
                    pokemonHorizontalAdapter.submitList(it.pokemons)
                    viewBinding.loadFavoritePokeError.isVisible = false
                    viewBinding.loadFavoritePokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = true
                }
            }
        }

        viewModel.screenStateSearched.observe(viewLifecycleOwner) {
            when (it) {
                is PokemonFavoriteState.Error -> {
                    viewBinding.loadPopularPokeError.isVisible = true
                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteState.Loading -> {
                    viewBinding.loadPopularPokeError.isVisible = false
                    viewBinding.loadPopularPokeProgress.isVisible = true
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteState.Success -> {
                    pokemonHorizontalAdapter2.submitList(it.pokemons)
                    viewBinding.loadPopularPokeError.isVisible = false
                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = true
                }
            }
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }
        viewBinding.pokemonSearch.setOnClickListener {
            openSearch()
        }


    }

    private fun openSearch() {
        parentFragmentManager.navigate(PokemonSearchFragment.newInstance())

    }

    private fun openDetail(pokemon: Pokemon) {
        parentFragmentManager.navigate(PokemonDetailFragment.newInstance(pokemon))
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "OnResume do updating")
        viewModel.onFragmentResume()
    }

}

