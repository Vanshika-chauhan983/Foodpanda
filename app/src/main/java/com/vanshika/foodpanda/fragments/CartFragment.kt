package com.vanshika.foodpanda.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return

                val ctx=context ?: return
                val cartList= mutableListOf<CartData>()
                for (item in snapshot.children){
                    val cartItem=item.getValue(CartData::class.java)
                    if (cartItem != null) {
                        cartList.add(cartItem)
                    }
                }
                cartRecyclerView.adapter=CartAdapter(ctx,cartList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ")
            }
        }
        databaseReference.addValueEventListener(cartListener!!)
    }

}