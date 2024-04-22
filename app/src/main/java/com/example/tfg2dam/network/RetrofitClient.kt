package com.example.tfg2dam.network

import com.example.tfg2dam.network.Const.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que proporciona una instancia de la API de videojuegos mediante Retrofit.
 */
object RetrofitClient {

    /**
     * Propiedad lazy que representa la instancia de la API de videojuegos.
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
