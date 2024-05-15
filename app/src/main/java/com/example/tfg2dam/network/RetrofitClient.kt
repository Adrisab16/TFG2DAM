package com.example.tfg2dam.network

import com.example.tfg2dam.network.Const.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto que proporciona una instancia única de Retrofit para interactuar con la API de videojuegos.
 */
object RetrofitClient {

    /**
     * Instancia única de la interfaz APIVideojuegos creada con Retrofit.
     * Utiliza lazy initialization para crear la instancia solo cuando se accede por primera vez.
     */
    val retrofit: APIVideojuegos by lazy {
        Retrofit
            .Builder()
            // Se especifica la URL base de la API.
            .baseUrl(BASE_URL)
            // Se agrega un convertidor de Gson para convertir JSON a objetos Kotlin.
            .addConverterFactory(GsonConverterFactory.create())
            // Se construye la instancia de Retrofit.
            .build()
            .create(APIVideojuegos::class.java)
    }
}