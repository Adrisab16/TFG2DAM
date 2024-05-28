package com.example.tfg2dam.network

//import com.example.tfg2dam.model.RawgResponse
import com.example.tfg2dam.model.VideoJuegoModel
import com.example.tfg2dam.model.VideojuegosLista
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que define los métodos para interactuar con la API de videojuegos.
 */
interface APIVideojuegos {

    /*
    /**
     * Método para obtener la lista de juegos desde la API.
     *
     * @return Objeto Response que contiene el modelo de datos de los videojuegos.
     */
    @GET("games${Const.API_KEY}")
    suspend fun obtenerJuegos(): Response<VideoJuegoModel>*/

    /**
     * Método para obtener la lista de los videojuegos mejor valorados según la puntuación de Metacritic.
     *
     * @return Objeto Response que contiene el modelo de datos de los videojuegos.
     */
    @GET("games${Const.API_KEY}")
    suspend fun obtenerJuegos(
        @Query("ordering") ordering: String = "+rating",
        @Query("platforms") platforms: String = "4", // 4 es el ID de la plataforma PC en RAWG API
        @Query("page_size") pageSize: Int = 40, // Doble del tamaño de página actual
        @Query("page") page: Int = 1
    ): Response<VideoJuegoModel>

    @GET("games${Const.API_KEY}")
    suspend fun buscarJuegos(
        @Query("search") query: String,
        @Query("search_precise") precise: Boolean = true,
        @Query("search_exact") exact: Boolean = true,
        @Query("platforms") platforms: String = "4",
        @Query("page_size") pageSize: Int = 4
    ): Response<VideoJuegoModel>

    /**
     * Método para obtener un juego específico por su ID desde la API.
     *
     * @param id ID del juego.
     * @return Objeto Response que contiene el modelo de datos del videojuego.
     */
    @GET("games/{id}${Const.API_KEY}")
    suspend fun obtenerJuegoPorId(@Path("id") id: Int): Response<VideojuegosLista>
}





