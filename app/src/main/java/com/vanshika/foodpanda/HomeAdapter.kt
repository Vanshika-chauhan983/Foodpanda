package com.vanshika.foodpanda

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeAdapter(val context: Context, private var list: List<Hit>) :RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var textView:TextView = itemView.findViewById(R.id.rv_textview)
        var textView2:TextView = itemView.findViewById(R.id.rv_textview2)
        var imageView:ImageView = itemView.findViewById(R.id.recyclerImage)
        var viewcount:TextView = itemView.findViewById(R.id.viewcount)
        private var favoriteButton:ImageButton = itemView.findViewById(R.id.FavRestaurant)

        private val favoriteStates = mutableMapOf<Int, Boolean>()

        fun bind(position: Int) {

            val isFavorite = favoriteStates[position] ?: false
            updateFavoriteIcon(isFavorite)

            favoriteButton.setOnClickListener {

                val newFavoriteState = !(favoriteStates[position] ?: false)
                favoriteStates[position] = newFavoriteState
                updateFavoriteIcon(newFavoriteState)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            val iconRes = if (isFavorite) R.drawable.filled_favorite else R.drawable.fav_restaurant
            val colorRes = if (isFavorite) R.color.red else R.color.black

            favoriteButton.setImageResource(iconRes)
            favoriteButton.setColorFilter(ContextCompat.getColor(itemView.context, colorRes))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(position)
        holder.textView.text = currentItem.user
        Glide.with(context).load(currentItem.largeImageURL).into(holder.imageView)
        holder.viewcount.text = "${currentItem.views}"
        holder.textView2.text = currentItem.tags
    }
}


