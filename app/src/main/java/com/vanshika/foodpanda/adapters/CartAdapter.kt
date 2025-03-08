package com.vanshika.foodpanda.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.vanshika.foodpanda.DataClasses.CartData
import com.vanshika.foodpanda.R

class CartAdapter(
    val context: Context,
    private val list: MutableList<CartData>,
    private val cartItemKeys: MutableList<String>
): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var foodname:TextView = view.findViewById(R.id.food_name)
        var price:TextView = view.findViewById(R.id.food_price)
        var delete:ImageButton = view.findViewById(R.id.deleteitem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=list[position]
        holder.foodname.text=currentItem.foodname
        holder.price.text= "Rs. ${currentItem.price}"

        val userid = FirebaseAuth.getInstance().currentUser?.uid

        holder.delete.setOnClickListener{
            val itemId = cartItemKeys[position]
            val databaseReference=
                userid?.let { it1 -> FirebaseDatabase.getInstance().getReference("User").child(it1).child("CartData").child(itemId)}
            if (databaseReference != null) {
                databaseReference.removeValue().addOnSuccessListener {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,list.size)
                    Toast.makeText(context,"Item removed!",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(context,"Failed to remove item.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}