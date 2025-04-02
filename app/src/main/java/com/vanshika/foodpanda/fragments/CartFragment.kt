package com.vanshika.foodpanda.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.vanshika.foodpanda.DataClasses.CartData
import com.vanshika.foodpanda.R
import com.vanshika.foodpanda.adapters.CartAdapter
import org.json.JSONObject

class CartFragment : Fragment(), PaymentResultListener {
    private lateinit var cartRecyclerView: RecyclerView
    private var cartListener: ValueEventListener? = null
    private lateinit var total: TextView
    private lateinit var paymentBtn: Button
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        paymentBtn = view.findViewById(R.id.orderBtn)
        cartRecyclerView = view.findViewById(R.id.cartRecycler)
        cartRecyclerView.layoutManager = LinearLayoutManager(activity)

        var cartAmount=0
        getData{sum->
            cartAmount=sum
        }
        paymentBtn.setOnClickListener {
            if (cartAmount>0){
                startPayment(cartAmount)
            }
            else{
                Toast.makeText(requireContext(),"Cart is empty!",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun getUserDetails(onSuccess:(String,String)->Unit){
        val databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId!!)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(35)
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val email=snapshot.child("email").getValue(String::class.java)
                    val phone=snapshot.child("number").getValue(String::class.java)
                    if (phone != null && email !=null)  {
                            onSuccess(email,phone)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase","Filed to fetch $error")
            }
        })
    }

    private fun startPayment(amount: Int) {
        getUserDetails{email,phone ->
            val activity=requireActivity()
            Checkout.preload(activity.applicationContext)
            val checkout = Checkout()

            checkout.setKeyID("rzp_test_Kq6S6Jej2xqf39")

            try {
                val obj = JSONObject().apply {
                    put("name", "FoodPanda")
                    put("order description", "Order Payment")
                    put("theme.color","#6d1b7b")
                    put("currency", "INR")
                    put("amount", amount*100)
                    put("prefill",JSONObject().apply {
                        put("email",email)
                        put("contact",phone)
                    })
                }

                checkout.open(activity,obj)
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),"Payment failed! ${e.message}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData(onSuccess: (Int) -> Unit) {

        val cartReference = FirebaseDatabase.getInstance().getReference("User").child(userId!!).child("CartData")

        cartListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return

                val ctx = context ?: return
                val cartList = mutableListOf<CartData>()
                val cartItemKeys = mutableListOf<String>()
                var sum = 0
                for (item in snapshot.children) {
                    val cartItem = item.getValue(CartData::class.java)
                    if (cartItem != null) {
                        cartList.add(cartItem)
                        cartItemKeys.add(item.key.toString())
                        sum += cartItem.price
                    }
                }
                onSuccess(sum)

                total = view?.findViewById(R.id.total) ?: return
                total.text = "Rs. $sum"
                cartRecyclerView.adapter = CartAdapter(ctx, cartList, cartItemKeys)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ")
            }
        }
        cartReference.addValueEventListener(cartListener!!)
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(requireContext(),"Payment Successful!",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("Payment failed!","$p1")
    }
}

