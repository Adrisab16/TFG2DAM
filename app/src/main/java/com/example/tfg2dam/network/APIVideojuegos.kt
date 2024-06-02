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

    /**
     * Método para obtener la lista de los videojuegos mejor valorados según la puntuación de Metacritic.
     *
     * @param ordering Orden en el que se devolverán los resultados.
     * @param platforms Plataformas para las que se obtendrán los videojuegos (por defecto, PC).
     * @param pageSize Tamaño de la página de resultados (por defecto, 40).
     * @param page Número de página de resultados (por defecto, 1).
     * @return Objeto Response que contiene el modelo de datos de los videojuegos.
     */
    @GET("games${Const.API_KEY}")
    suspend fun obtenerJuegos(
        @Query("ordering") ordering: String = "+rating", // Con esta query lo ordeno de mejor puntuacion a peor
        @Query("platforms") platforms: String = "4", // 4 es el ID de la plataforma PC en RAWG API
        @Query("page_size") pageSize: Int = 40, // Doble del tamaño de página actual
        @Query("page") page: Int = 1 // Esta query indica que solo mostrará la primera página
    ): Response<VideoJuegoModel>

    /**
     * Método para buscar juegos por nombre.
     *
     * @param query Término de búsqueda.
     * @param precise Indica si la búsqueda debe ser precisa.
     * @param exact Indica si la búsqueda debe ser exacta.
     * @param platforms Plataformas para las que se realizará la búsqueda (por defecto, PC).
     * @param pageSize Tamaño de la página de resultados (por defecto, 4).
     * @return Objeto Response que contiene el modelo de datos de los videojuegos.
     */
    @GET("games${Const.API_KEY}")
    suspend fun buscarJuegos(
        @Query("search") query: String, // Esta query indica que la intencion es buscar en la API
        @Query("search_precise") precise: Boolean = true, // Esta query indica que la busqueda ha de ser precisa, es decir, que la busqueda ha de tener todos los caracteres bien escritos
        @Query("search_exact") exact: Boolean = true, // Esta query indica que la busqueda ha de ser precisa, es decir, que la busqueda ha de tener todos los caracteres bien escritos
        @Query("platforms") platforms: String = "4", // Esta query indica que solo mostrará resultados que estén disponibles para PC
        @Query("page_size") pageSize: Int = 4 // Esta query indica que solo mostrará los 4 primeros resultados
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