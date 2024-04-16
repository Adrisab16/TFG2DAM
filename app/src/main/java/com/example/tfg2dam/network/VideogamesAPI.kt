package com.example.tfg2dam.network

import com.example.tfg2dam.Const
import com.example.tfg2dam.model.GameModel
import retrofit2.http.GET

interface VideogamesAPI {
    @GET("games${Const.API_KEY}")
    suspend fun getGames(): GameModel
}
