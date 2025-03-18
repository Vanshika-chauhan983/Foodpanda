package com.vanshika.foodpanda.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.vanshika.foodpanda.DataClasses.CartData
import com.vanshika.foodpanda.DataClasses.Result
import com.vanshika.foodpanda.R
import java.math.RoundingMode

class HomeAdapter(val context: Context, private var list: List<Result>) :RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var textView:TextView = itemView.findViewById(R.id.rv_textview)
        var textView2:TextView = itemView.findViewById(R.id.rv_textview2)
        var imageView:ImageView = itemView.findViewById(R.id.recyclerImage)
        var rating:TextView = itemView.findViewById(R.id.ratingtext)
        private var favoriteButton:ImageButton = itemView.findViewById(R.id.FavRestaurant)
        var addBtn:Button = itemView.findViewById(R.id.addBtn)

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

    @RequiresApi(35)
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(position)
        val foodItem=currentItem.name
        holder.textView.text = currentItem.name

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.profile_icon)
            .error(R.drawable.profile_icon)

        Glide.with(context).load(currentItem.thumbnail_url).apply(options).into(holder.imageView)
        val price=currentItem.price.total
        holder.textView2.text = "Rs. ${currentItem.price.total}"

        fun multiply(): String {
            val rating=currentItem.user_ratings.score.toBigDecimal().setScale(2,RoundingMode.FLOOR)
            return (rating*10.toBigDecimal()).toString()

        }
        holder.rating.text = "${multiply()}/10"

        holder.addBtn.setOnClickListener{
            val userid = FirebaseAuth.getInstance().currentUser?.uid

            if (userid != null) {
                val databaseReference=FirebaseDatabase.getInstance().getReference("User")
                val cartItem=CartData(foodItem,price)
                databaseReference.child(userid).child("CartData").push().setValue(cartItem).addOnSuccessListener {
                    Toast.makeText(context,"Item added to cart successfully!",Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{
                    Toast.makeText(context,"Failed!",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}



