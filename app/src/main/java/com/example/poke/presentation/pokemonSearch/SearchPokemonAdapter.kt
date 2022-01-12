package com.example.poke.presentation.pokemonSearch

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.poke.databinding.ItemPokemonBinding
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
            root.setBackgroundColor(Color.parseColor("#40${"%06x".format(item.color)}"))
            root.setOnClickListener { onPokemonClicked(item) }
        }
    }

    class ViewHolder(val binding: ItemSearchPokemonBinding): RecyclerView.ViewHolder(binding.root)

}
