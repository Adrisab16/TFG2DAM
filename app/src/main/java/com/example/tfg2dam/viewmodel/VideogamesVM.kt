package com.example.tfg2dam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg2dam.model.VideogamesList
import com.example.tfg2dam.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideogamesVM: ViewModel() {

    private val _games = MutableStateFlow<List<VideogamesList>>(emptyList())
    val games = _games.asStateFlow()

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.Main){
                val response = RetrofitClient.api.getGames()
                // _games.value = response.body()?.VideogamesList ?: emptyList()
            }
        }
    }
}