package com.example.poke.presentation.pokemonFavorite

import android.os.Bundle
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
import com.example.poke.presentation.pokemonFavorite.PokemonFavoriteViewModel_Factory.newInstance
import com.example.poke.presentation.pokemonSearch.PokemonSearchFragment
import com.example.poke.presentation.pokemonSearch.PokemonSearchViewModel_Factory.newInstance
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
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.screenState.observe(viewLifecycleOwner) {
            when (it) {
                is PokemonFavoriteState.Error -> {
                    viewBinding.loadFavoritePokeError.isVisible = true
                    viewBinding.loadPopularPokeError.isVisible = true
                    viewBinding.loadFavoritePokeProgress.isVisible = false
                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteState.Loading -> {
                    viewBinding.loadFavoritePokeError.isVisible = false
                    viewBinding.loadPopularPokeError.isVisible = false
                    viewBinding.loadFavoritePokeProgress.isVisible = true
                    viewBinding.loadPopularPokeProgress.isVisible = true
                    viewBinding.favoritesPokeView.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteState.Success -> {
                    pokemonHorizontalAdapter.submitList(it.pokemons)
                    viewBinding.loadFavoritePokeError.isVisible = false
                    viewBinding.loadPopularPokeError.isVisible = false
                    viewBinding.loadFavoritePokeProgress.isVisible = false
                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = true
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
    sealed class PokemonFavoriteState {
        class Loading() : PokemonFavoriteState()
        class Success(val pokemons: List<Pokemon>) : PokemonFavoriteState()
        class Error(val throwable: Throwable) : PokemonFavoriteState()
    }


    private fun openSearch() {
        parentFragmentManager.navigate(PokemonSearchFragment.newInstance())

    }
}

    private fun openDetail(film: com.example.poke.domain.entity.Pokemon) {
        //parentFragmentManager.navigate(FilmDetailFragment.newInstance(film))
    }

