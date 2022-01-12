//package com.example.poke.presentation.pokemonFavorite
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//
//import androidx.recyclerview.widget.RecyclerView
//import com.example.poke.R
//import com.example.poke.presentation.pokemonSearch.SearchPokemonAdapter
//
//import com.squareup.picasso.Picasso
//import me.sargunvohra.lib.pokekotlin.model.Pokemon
//
//class PokemonHorizontalAdapter (private val onPokemonClicked: (Pokemon) -> Unit) :
//    RecyclerView.Adapter<PokemonHorizontalAdapter.MyView>() {
//    class MyView(view: View) : RecyclerView.ViewHolder(view) {
//        var pokemon: Pokemon
//        init {
//            pokemon = view
//                .findViewById<Pokemon>(R.id.ite)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
//        val itemView: View = LayoutInflater
//            .from(parent.context)
//            .inflate(
//                R.layout.item_pokemon,
//                parent,
//                false
//            )
//        return MyView(itemView)
//    }
//
//    override fun onBindViewHolder(holder: PokemonHorizontalAdapter.MyView, position: Int) {
//        with (holder.binding) {
//            val item = getItem(position)
//            itemPokemonName.text = item.name
//            root.setOnClickListener { onPokemonClicked(item) }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//}
//

package com.example.poke.presentation.pokemonFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.poke.databinding.ItemPokemonBinding
import com.example.poke.domain.entity.Pokemon
import kotlin.reflect.KFunction1

class PokemonHorizontalAdapter(
    private val onPokemonClicked: KFunction1<Pokemon, Unit>
) : ListAdapter<Pokemon, PokemonHorizontalAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem
    }
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            itemPokemonName.text = item.name
            itemPokemonId.text = item.id.toString()
            root.setOnClickListener { onPokemonClicked(item) }
        }
    }


    class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

}
