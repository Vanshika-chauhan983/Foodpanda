package com.vanshika.foodpanda.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.foodpanda.DataClasses.CartData
import com.vanshika.foodpanda.R

class CartAdapter(val context: Context, val list: List<CartData>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var foodname:TextView = view.findViewById(R.id.food_name)
        var price:TextView = view.findViewById(R.id.food_price)
        var total:Button = view.findViewById(R.id.orderBtn)
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
        var sum=0
        holder.foodname.text=currentItem.foodname
        holder.price.text= "Rs. ${currentItem.price}"

        for (x in list){
            sum=sum+x.price
        }
        holder.total.text= sum.toString()
    }

}