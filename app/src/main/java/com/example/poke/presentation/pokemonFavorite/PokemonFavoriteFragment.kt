package com.example.poke.presentation.pokemonFavorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.poke.R
import com.example.poke.databinding.TopPokeScreenBinding
import com.example.poke.presentation.common.BaseFragment
import com.example.poke.presentation.pokemonSearch.SearchPokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.sargunvohra.lib.pokekotlin.model.Pokemon

@AndroidEntryPoint
class PokemonFavoriteFragment : BaseFragment(R.layout.top_poke_screen) {

    private val viewBinding by viewBinding(TopPokeScreenBinding::bind)
    private val viewModel by viewModels<PokemonFavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchPokemonAdapter = SearchPokemonAdapter(viewModel::onPokemonClicked)
        with(viewBinding.favoritesPokeView) {
            adapter = searchPokemonAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonFavoriteViewModel.PokemonFavoriteState.Error -> {
//                    viewBinding.loadFavoritePokeError.isVisible = true
//                    viewBinding.loadPopularPokeError.isVisible = true
//                    viewBinding.loadFavoritePokeProgress.isVisible = false
//                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteViewModel.PokemonFavoriteState.Loading -> {
//                    viewBinding.loadFavoritePokeError.isVisible = false
//                    viewBinding.loadPopularPokeError.isVisible = false
//                    viewBinding.loadFavoritePokeProgress.isVisible = true
//                    viewBinding.loadPopularPokeProgress.isVisible = true
                    viewBinding.favoritesPokeView.isVisible = false
                    viewBinding.mostPopularPokeView.isVisible = false
                }
                is PokemonFavoriteViewModel.PokemonFavoriteState.Success -> {
                    searchPokemonAdapter.submitList(state.pokemons)
//                    viewBinding.loadFavoritePokeError.isVisible = false
//                    viewBinding.loadPopularPokeError.isVisible = false
//                    viewBinding.loadFavoritePokeProgress.isVisible = false
//                    viewBinding.loadPopularPokeProgress.isVisible = false
                    viewBinding.favoritesPokeView.isVisible = true
                    viewBinding.mostPopularPokeView.isVisible = true
                }
            }
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }


    }

    private fun openDetail(film: Pokemon) {
        //parentFragmentManager.navigate(FilmDetailFragment.newInstance(film))
    }


}