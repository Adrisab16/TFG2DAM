package com.example.tfg2dam

import com.example.tfg2dam.model.RawgResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApiService {
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") search: String? = null
    ): RawgResponse
}
