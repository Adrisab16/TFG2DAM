package com.example.tfg2dam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para manejar la información de los videojuegos.
 */
class VideojuegosViewModel : ViewModel() {
    // Lista mutable de videojuegos como StateFlow para mantener la actualización de los datos
    private val _juegos = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val juegos = _juegos.asStateFlow() // Convertir la lista mutable en un flujo de estado inmutable

    private val _searchResults = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val searchResults = _searchResults.asStateFlow() // Lista de juegos buscados

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

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
            withContext(Dispatchers.Main) {
                val response = RetrofitClient.retrofit.obtenerJuegos()
                _juegos.value = response.body()?.listaVideojuegos ?: emptyList()
            }
        }
    }

    /**
     * Obtiene la imagen de un juego por su ID.
     *
     * @param id ID del juego.
     * @return URL de la imagen del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getGameNameById(id: Int): String {
        // Accede a la lista de juegos desde el StateFlow
        val listaJuegos = _juegos.value
        // Buscar el juego en la lista por su ID
        val juego = listaJuegos.find { it.id == id }
        // Devolver el nombre del juego si se encuentra, de lo contrario, devolver una cadena vacía
        return juego?.name ?: ""
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
    fun getGameImageById(id: Int): String {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.image ?: ""
    }

    /**
     * Obtiene la puntuación Metacritic de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Puntuación Metacritic del juego si se encuentra, de lo contrario, 0.
     */
    fun getGameMcScoreById(id: Int): Int {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.mcscore ?: 0
    }

    /**
     * Obtiene el tiempo de juego de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Tiempo de juego del juego si se encuentra, de lo contrario, 0.
     */
    fun getGamePlayTimeById(id: Int): Int {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.gameplaytime ?: 0
    }

    /**
     * Obtiene la fecha de lanzamiento de un juego por su ID.
     *
     * @param id ID del juego.
     * @return Fecha de lanzamiento del juego si se encuentra, de lo contrario, una cadena vacía.
     */
    fun getGameDateById(id: Int): Comparable<*> {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.datereleased ?: ""
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