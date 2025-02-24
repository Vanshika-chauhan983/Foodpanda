package com.vanshika.foodpanda

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView=view.findViewById(R.id.homeRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        getData()
        return view
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://tasty.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Food::class.java)

        val retrofitData=retrofitBuilder.getImage()

        retrofitData.enqueue(object : Callback<FoodData>{
            override fun onResponse(p0: Call<FoodData>, p1: Response<FoodData>) {
                val responseBody=p1.body()
                homeAdapter= HomeAdapter(activity as Context, responseBody!!.results)
                recyclerView.adapter = homeAdapter
            }

            override fun onFailure(p0: Call<FoodData>, p1: Throwable) {
                Log.d("message",p1.message.toString())
            }
        })
    }
}