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
}













