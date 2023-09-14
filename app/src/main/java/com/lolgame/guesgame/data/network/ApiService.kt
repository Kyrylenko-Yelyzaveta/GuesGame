package com.lolgame.guesgame.data.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("integers/?num=1&min=1&max=100&col=1&&base=10&format=plain&rnd=new") // Replace with the actual endpoint
    fun getInteger(): Call<Int>
}