package com.vanshika.foodpanda

import retrofit2.Call
import retrofit2.http.GET

interface Flowers {

    @GET("api/?key=43212139-9084eb4570b1a38345537b91c&q=yellow+flowers&image_type=photo&pretty=true")
    fun getImage():Call<MyData>
}