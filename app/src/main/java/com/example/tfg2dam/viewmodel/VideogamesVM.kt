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
    val juegos: StateFlow<List<VideojuegosLista>> = _juegos.asStateFlow()

    private val _searchResults = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val searchResults: StateFlow<List<VideojuegosLista>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched

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

    // Nuevo método para obtener un juego por su ID
    fun obtenerJuegosPorIds(gameIds: List<Int>, onJuegosObtenidos: (List<VideojuegosLista>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val juegosObtenidos = mutableListOf<VideojuegosLista>()
            for (id in gameIds) {
                try {
                    val response = RetrofitClient.retrofit.obtenerJuegoPorId(id)
                    val juego = response.body()
                    if (juego != null) {
                        juegosObtenidos.add(juego)
                    }
                } catch (e: Exception) {
                    Log.e("ERROR", "Error al obtener juego por ID: ${e.localizedMessage}")
                }
            }
            withContext(Dispatchers.Main) {
                onJuegosObtenidos(juegosObtenidos)
            }
        }
    }

    fun obtenerJuegoPorId(gameId: Int, onJuegosObtenidos: (List<VideojuegosLista>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val juegosObtenidos = mutableListOf<VideojuegosLista>()
            try {
                val response = RetrofitClient.retrofit.obtenerJuegoPorId(gameId)
                val juego = response.body()
                if (juego != null) {
                    juegosObtenidos.add(juego)
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Error al obtener juego por ID: ${e.localizedMessage}")
            }
            withContext(Dispatchers.Main) {
                onJuegosObtenidos(juegosObtenidos)
            }
        }
    }

    fun buscarJuegos(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _hasSearched.value = true
            try {
                val response = RetrofitClient.retrofit.buscarJuegos(query)
                _searchResults.value = response.body()?.listaVideojuegos ?: emptyList()
            } catch (e: Exception) {
                Log.e("ERROR", "Error al buscar juegos: ${e.localizedMessage}")
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}