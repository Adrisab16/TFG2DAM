package com.example.tfg2dam.network

//import com.example.tfg2dam.model.RawgResponse
import com.example.tfg2dam.model.VideoJuegoModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz que define los métodos para interactuar con la API de videojuegos.
 */
interface APIVideojuegos {

    /**
     * Método para obtener la lista de juegos desde la API.
     *
     * @return Objeto Response que contiene el modelo de datos de los videojuegos.
     */
    @GET("games${Const.API_KEY}")
    suspend fun obtenerJuegos(): Response<VideoJuegoModel>

}





