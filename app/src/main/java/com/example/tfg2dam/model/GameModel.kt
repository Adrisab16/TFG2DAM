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
)