package com.example.tfg2dam.network

import com.example.tfg2dam.Const.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: VideogamesAPI by lazy {
        val retrofit =
            Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideogamesAPI::class.java)
    }
}