package com.example.poke.presentation.pokemonSearch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.poke.R
import com.example.poke.databinding.PokemonSearchScreenBinding
import com.example.poke.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import me.sargunvohra.lib.pokekotlin.model.Pokemon

@AndroidEntryPoint
class PokemonSearchFragment : BaseFragment(R.layout.pokemon_search_screen) {

    private val viewBinding by viewBinding(PokemonSearchScreenBinding::bind)
    private val viewModel by viewModels<PokemonSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchPokemonAdapter = SearchPokemonAdapter(viewModel::onPokemonClicked)
        with (viewBinding.searchResultList) {
            adapter = searchPokemonAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonSearchState.Error -> {
                    viewBinding.searchPokemonError.isVisible = true
                    viewBinding.searchProgress.isVisible = false
                    viewBinding.searchResultList.isVisible = false
                }
                is PokemonSearchState.Loading -> {
                    viewBinding.searchPokemonError.isVisible = false
                    viewBinding.searchProgress.isVisible = true
                    viewBinding.searchResultList.isVisible = false
                    viewBinding.searchSubmit.isVisible = false
                }
                is PokemonSearchState.Success -> {
                    searchPokemonAdapter.submitList(state.pokemons)
                    viewBinding.searchPokemonError.isVisible = false
                    viewBinding.searchProgress.isVisible = false
                    viewBinding.searchResultList.isVisible = true
                    viewBinding.searchSubmit.isVisible = true
                }
            }
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }

        viewBinding.searchSubmit.setOnClickListener {
            viewModel.pokemonSearch(viewBinding.searchPokemonNameEdit.text.toString())
        }

    }

    private fun openDetail(film: Pokemon) {
        //parentFragmentManager.navigate(FilmDetailFragment.newInstance(film))
    }


}