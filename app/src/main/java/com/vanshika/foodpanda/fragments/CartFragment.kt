package com.vanshika.foodpanda.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanshika.foodpanda.DataClasses.CartData
import com.vanshika.foodpanda.R
import com.vanshika.foodpanda.adapters.CartAdapter

class CartFragment : Fragment() {
    private lateinit var cartRecyclerView:RecyclerView
    private var cartListener:ValueEventListener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_cart, container, false)
        cartRecyclerView = view.findViewById(R.id.cartRecycler)
        cartRecyclerView.layoutManager=LinearLayoutManager(activity)
        getdata()
        return view
    }

    private fun getdata() {
        val userid=FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference=FirebaseDatabase.getInstance().getReference("User").child((userid!!)).child("CartData")

        cartListener=object : ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return

                val ctx=context ?: return
                val cartList = mutableListOf<CartData>()
                val cartItemKeys = mutableListOf<String>()
                var sum=0
                for (item in snapshot.children){
                    val cartItem=item.getValue(CartData::class.java)
                    if (cartItem != null) {
                        cartList.add(cartItem)
                        cartItemKeys.add(item.key.toString())
                        sum+=cartItem.price
                    }
                }
                val total: TextView = view?.findViewById(R.id.total) ?: return
                total.text= "Rs. $sum"
                cartRecyclerView.adapter=CartAdapter(ctx,cartList,cartItemKeys)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ")
            }
        }
        databaseReference.addValueEventListener(cartListener!!)
    }
}

