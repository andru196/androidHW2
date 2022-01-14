package com.example.poke.presentation.pokemonSearch

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.poke.databinding.ItemSearchPokemonBinding

import com.example.poke.domain.entity.Pokemon
import kotlin.reflect.KFunction1

class SearchPokemonAdapter(
    private val onPokemonClicked: KFunction1<Pokemon, Unit>
) : ListAdapter<Pokemon, SearchPokemonAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem
    }
) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder.binding) {
            val item = getItem(position)

            itemPokemonName.text = item.name
            val gd = GradientDrawable()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                gd.shape = (searchIndicator.background as GradientDrawable).shape
            }
            searchIndicator.background = gd
            gd.colors = intArrayOf(
                Color.parseColor("#${"%06x".format(item.color)}"),
                Color.parseColor("#80${"%06x".format(item.color)}")
            )

//            (searchIndicator.background as GradientDrawable).setColors(intArrayOf(
//                Color.parseColor("#${"%06x".format(item.color)}"),
//                Color.parseColor("#80${"%06x".format(item.color)}")
//            ))
            root.setBackgroundColor(Color.parseColor("#10${"%06x".format(item.color)}"))
            root.setOnClickListener { onPokemonClicked(item) }

        }
    }

    class ViewHolder(val binding: ItemSearchPokemonBinding): RecyclerView.ViewHolder(binding.root)

}
