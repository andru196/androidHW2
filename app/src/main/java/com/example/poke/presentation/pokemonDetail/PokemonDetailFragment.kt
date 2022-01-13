package com.example.poke.presentation.pokemonDetail

import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.poke.R
import com.example.poke.databinding.PokemonDetailScreenBinding
import com.example.poke.domain.entity.Pokemon
import com.example.poke.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailFragment : BaseFragment(R.layout.pokemon_detail_screen) {

    companion object {
        fun newInstance(pokemon: Pokemon) = PokemonDetailFragment().apply {
            arguments = bundleOf(POKEMON_DETAIL_DATA_KEY to pokemon)

        }

        private const val POKEMON_DETAIL_DATA_KEY = "POKEMON_DETAIL_DATA_KEY"


    }

    @Inject
    lateinit var pokemonDetailViewModelFactory: PokemonDetailViewModel.Factory

    private val viewBinding by viewBinding(PokemonDetailScreenBinding::bind)
    private val viewModel by viewModels<PokemonDetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                pokemonDetailViewModelFactory.create(arguments?.getParcelable(
                    POKEMON_DETAIL_DATA_KEY)!!) as T
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemonState.observe(viewLifecycleOwner) { pokemon ->
            viewBinding.pokeDetailName.text = pokemon.name
            viewBinding.pokeDetailIdSubject.text = pokemon.id.toString()
            viewBinding.pokeDetailHeightSubject.text = pokemon.height.toString()
            viewBinding.pokeDetailIsDefaultSubject.text = pokemon.is_default.toString()
            viewBinding.pokeDetailBaseExpSubject.text = pokemon.baseExp.toString()
            someFun(viewBinding.pokeDetailCard, pokemon.color)
        }
        viewModel.backAction.observe(viewLifecycleOwner) {
            closeScreen()
        }
        viewBinding.pokeDetailBack.setOnClickListener {
            viewModel.onBackPressed()
        }


    }

    private fun closeScreen() {
        parentFragmentManager.popBackStack()
    }

    private fun someFun(content: View, color: Int) {
        val start = Color.parseColor("#${"%06x".format(color)}")
        val mid = Color.parseColor("#${"%06x".format(color.inv())}")
        val end = start

        content.background = GradientDrawable()
        val gradient = content.background as GradientDrawable

        val evaluator = ArgbEvaluator()
        val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
        animator.duration = 15000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.addUpdateListener {
            val fraction = it.animatedFraction
            val newStart = evaluator.evaluate(fraction, start, end) as Int
            val newMid = evaluator.evaluate(fraction, mid, start) as Int
            val newEnd = evaluator.evaluate(fraction, end, mid) as Int

            gradient.colors = intArrayOf(newStart, newMid, newEnd)
        }

        animator.start()
    }


}
