package com.example.tfg2dam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideojuegosViewModel : ViewModel() {
    private val _juegos = MutableStateFlow<List<VideojuegosLista>>(emptyList())
    val juegos = _juegos.asStateFlow()

    init {
        obtenerJuegos()
    }
    private fun obtenerJuegos(){
        viewModelScope.launch(Dispatchers.IO) {
            withContext((Dispatchers.Main)) {
                val response = RetrofitClient.retrofit.obtenerJuegos()
                _juegos.value = response.body()?.listaVideojuegos ?: emptyList()
            }
        }
    }

    fun getGameNameById(id: Int): String {
        // Accede a la lista de juegos desde el StateFlow
        val listaJuegos = _juegos.value
        // Buscar el juego en la lista por su ID
        val juego = listaJuegos.find { it.id == id }
        // Devolver el nombre del juego si se encuentra, de lo contrario, devolver una cadena vacía
        return juego?.name ?: ""
    }

    fun getGameImageById(id: Int): String {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.image ?: ""
    }

    fun getGameMcScoreById(id: Int): Int {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.mcscore ?: 0
    }

    fun getGamePlayTimeById(id: Int): Int {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.gameplaytime ?: 0
    }

    fun getGameDateById(id: Int): Comparable<*> {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.datereleased ?: ""
    }

    /*
    Consultar GameModel para info
    fun getGameDescById(id: Int): String {
        val listaJuegos = _juegos.value
        val juego = listaJuegos.find { it.id == id }
        return juego?.gamedesc ?: ""
    }
    */

}













