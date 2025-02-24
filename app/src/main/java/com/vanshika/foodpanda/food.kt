package com.vanshika.foodpanda

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Food {
    @Headers("x-rapidapi-key:a91d088c73msh6a28de416ae89fbp14e8d6jsnb593d103168e")

    @GET("recipes/list?from=0&size=20&tags=under_30_minutes")
    fun getImage():Call<FoodData>
}



