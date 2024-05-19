package com.example.tfg2dam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para manejar la información de los videojuegos.
 */
class VideojuegosViewModel : ViewModel() {
    // Lista mutable de videojuegos como StateFlow para mantener la actualización de los datos
    private val _juegos = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val juegos: StateFlow<List<VideojuegosLista>> = _juegos.asStateFlow()

    private val _searchResults = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val searchResults: StateFlow<List<VideojuegosLista>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // Variable combinada de juegos y resultados de búsqueda
    val combinedJuegos = combine(juegos, searchResults) { juegos, searchResults ->
        juegos + searchResults
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * Inicializa el ViewModel y llama a la función para obtener la lista de videojuegos.
     */
    init {
        obtenerJuegos()
    }

    /**
     * Función para obtener la lista de videojuegos desde un servicio web utilizando Retrofit.
     */
    private fun obtenerJuegos() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.retrofit.obtenerJuegos()
            _juegos.value = response.body()?.listaVideojuegos ?: emptyList()
        }
    }

    /*

    Este codigo habilita la busqueda de todos los juegos de la API, pero hace que la app se vuelva MUY lenta

    private fun obtenerJuegos() {
        viewModelScope.launch(Dispatchers.IO) {
            val allGames = obtenerTodosLosJuegos()
            _juegos.value = allGames
        }
    }

    private suspend fun obtenerTodosLosJuegos(): List<VideojuegosLista> {
        val todosLosJuegos = mutableListOf<VideojuegosLista>()

        var pagina = 1
        var juegosEnPagina: List<VideojuegosLista>

        do {
            val response = RetrofitClient.retrofit.obtenerJuegos(page = pagina)
            juegosEnPagina = response.body()?.listaVideojuegos ?: emptyList()
            todosLosJuegos.addAll(juegosEnPagina)
            pagina++
        } while (juegosEnPagina.isNotEmpty())

        Log.i("TODOS LOS JUEGOS", "$todosLosJuegos")
        return todosLosJuegos
    }*/

    /**
     * Obtiene la imagen de un juego por su ID.
     *
     * @param id ID del juego.
     * @return URL de la imagen del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getGameNameById(id: Int, searched: Boolean): String {
        if(searched){
            // Accede a la lista de juegos desde el StateFlow
            val listaJuegos = _searchResults.value
            // Buscar el juego en la lista por su ID
            val juego = listaJuegos.find { it.id == id }
            // Devolver el nombre del juego si se encuentra, de lo contrario, devolver una cadena vacía
            return juego?.name ?: ""
        }
        else{
            // Accede a la lista de juegos desde el StateFlow
            val listaJuegos = _juegos.value
            // Buscar el juego en la lista por su ID
            val juego = listaJuegos.find { it.id == id }
            // Devolver el nombre del juego si se encuentra, de lo contrario, devolver una cadena vacía
            return juego?.name ?: ""
        }
    }

    /**
     * Obtiene el nombre corto del juego por su ID, limitado a las dos primeras palabras.
     *
     * @param id ID del juego.
     * @return Nombre corto del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getShortGameNameById(id: Int): String {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        val fullName = juego?.name ?: ""
        // Cortar el nombre a las dos primeras palabras
        val words = fullName.split(" ")
        return if (words.size >= 2) {
            "${words[0]} ${words[1]}"
        } else {
            fullName
        }
    }

    /**
     * Obtiene la imagen de un juego por su ID.
     *
     * @param id ID del juego.
     * @return URL de la imagen del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getGameImageById(id: Int, searched: Boolean): String {
        return if(searched){
            val listaJuegos = _searchResults.value
            val juego = listaJuegos.find { it.id == id }
            juego?.image ?: ""
        }else{
            val listaJuegos = _juegos.value
            val juego = listaJuegos.find { it.id == id }
            juego?.image ?: ""
        }
    }

    /**
     * Obtiene la puntuación Metacritic de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Puntuación Metacritic del juego si se encuentra, de lo contrario, 0.
     */
    fun getGameMcScoreById(id: Int, searched: Boolean): Int {
        return if(searched) {
            val listaJuegos = _searchResults.value
            val juego = listaJuegos.find { it.id == id }
            juego?.mcscore ?: 0
        } else{
            val listaJuegos = _juegos.value
            val juego = listaJuegos.find { it.id == id }
            juego?.mcscore ?: 0
        }
    }

    /**
     * Obtiene el tiempo de juego de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Tiempo de juego del juego si se encuentra, de lo contrario, 0.
     */
    fun getGamePlayTimeById(id: Int, searched: Boolean): Int {
        return if(searched){
            val listaJuegos = _searchResults.value
            val juego = listaJuegos.find { it.id == id }
            juego?.gameplaytime ?: 0
        }else{
            val listaJuegos = _juegos.value
            val juego = listaJuegos.find { it.id == id }
            juego?.gameplaytime ?: 0
        }
    }

    /**
     * Obtiene la fecha de lanzamiento de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Fecha de lanzamiento del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getGameDateById(id: Int, searched: Boolean): Comparable<*> {
        return if(searched){
            val listaJuegos = _searchResults.value
            val juego = listaJuegos.find { it.id == id }
            juego?.datereleased ?: ""
        }else{
            val listaJuegos = _juegos.value
            val juego = listaJuegos.find { it.id == id }
            juego?.datereleased ?: ""
        }
    }

    fun buscarJuegos(query: String) {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                val response = RetrofitClient.retrofit.buscarJuegos(query)
                _searchResults.value = response.body()?.listaVideojuegos ?: emptyList()
            } catch (e: Exception) {
                Log.e("ERROR", "Error al buscar juegos: ${e.localizedMessage}")
                _searchResults.value = emptyList()
            } finally {
                _isSearching.value = false
            }
        }
    }
}