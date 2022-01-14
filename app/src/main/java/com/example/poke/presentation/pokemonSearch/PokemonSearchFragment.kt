package com.example.poke.presentation.pokemonSearch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.poke.R
import com.example.poke.databinding.PokemonSearchScreenBinding
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.BaseFragment
import com.example.poke.presentation.common.navigate
import com.example.poke.presentation.pokemonDetail.PokemonDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonSearchFragment : BaseFragment(R.layout.pokemon_search_screen) {

    companion object {
        fun newInstance() = PokemonSearchFragment()
    }

    private val viewBinding by viewBinding(PokemonSearchScreenBinding::bind)
    private val viewModel by viewModels<PokemonSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchPokemonAdapter = SearchPokemonAdapter(viewModel::onPokemonClicked)
        with(viewBinding.searchResultList) {
            adapter = searchPokemonAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            viewBinding.searchPokemonError.isVisible = false
            viewBinding.searchProgress.isVisible = false
            viewBinding.searchResultList.isVisible = true
            viewBinding.searchSubmit.isEnabled = true
            viewBinding.searchSubmit.isVisible = true

            when (state) {
                is PokemonSearchState.Error -> {
                    viewBinding.searchPokemonError.isVisible = true
                    viewBinding.searchResultList.isVisible = false


                }
                is PokemonSearchState.Loading -> {
                    viewBinding.searchProgress.isVisible = true
                    viewBinding.searchResultList.isVisible = false
                    viewBinding.searchSubmit.isEnabled = false

                }
                is PokemonSearchState.Success -> {
                    searchPokemonAdapter.submitList(state.pokemons)
                    viewBinding.searchSubmit.isVisible = false
                    viewBinding.searchSubmit.isVisible = false


                }
                is PokemonSearchState.Editing -> {
                    searchPokemonAdapter.submitList(state.pokemons)
                }

            }

            viewBinding.searchPokemonNameEdit.doOnTextChanged { text, _, _, _ ->
                viewModel.searchTextChanged(text.toString())
            }

            viewBinding.searchPokemonBackImage.setOnClickListener {
                goBack()
            }
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }

        viewBinding.searchSubmit.setOnClickListener {
            viewModel.pokemonSearch(viewBinding.searchPokemonNameEdit.text.toString())
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun openDetail(pokemon: Pokemon) {
        parentFragmentManager.navigate(PokemonDetailFragment.newInstance(pokemon))
    }


}