package com.example.tfg2dam.network

//import com.example.tfg2dam.model.RawgResponse
import com.example.tfg2dam.model.VideoJuegoModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz que describe las operaciones disponibles para interactuar con la API de videojuegos.
 */
interface APIVideojuegos {

    @GET("games${Const.API_KEY}")
    suspend fun obtenerJuegos(): Response<VideoJuegoModel>

}





