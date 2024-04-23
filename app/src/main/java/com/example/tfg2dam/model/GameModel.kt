package com.example.tfg2dam.model

import com.google.gson.annotations.SerializedName

data class VideoJuegoModel(
    @SerializedName("counts")
    val count: Int,
    @SerializedName("results")
    val listaVideojuegos: List<VideojuegosLista>
)

data class VideojuegosLista(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val image: String,
    @SerializedName("metacritic")
    val mcscore: Int,
    @SerializedName("released")
    val datereleased: String,
    @SerializedName("playtime")
    val gameplaytime: Int,

    /*
    No existe descripción en esta lista, he de buscarlo de otra formas
    @SerializedName("description")
    val gamedesc: String,
    */
)