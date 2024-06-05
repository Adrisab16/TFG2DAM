package com.example.tfg2dam.model

import com.google.gson.annotations.SerializedName

/**
 * Data class que representa el modelo de datos para una colección de videojuegos.
 *
 * @property count Número total de videojuegos en la colección.
 * @property listaVideojuegos Lista de objetos `VideojuegosLista` que contiene los detalles de cada videojuego.
 */

data class VideoJuegoModel(
    @SerializedName("counts")
    val count: Int, // Número total de videojuegos
    @SerializedName("results")
    val listaVideojuegos: List<VideojuegosLista> // Lista de videojuegos
)

/**
 * Data class que representa los detalles de un videojuego individual.
 *
 * @property id Identificador único del videojuego.
 * @property name Nombre del videojuego.
 * @property image URL de la imagen de fondo del videojuego.
 * @property mcscore Puntuación del videojuego en Metacritic.
 * @property datereleased Fecha de lanzamiento del videojuego.
 * @property gameplaytime Tiempo de juego estimado en horas.
 */

data class VideojuegosLista(
    @SerializedName("id")
    val id: Int, // Identificador único del videojuego
    @SerializedName("name")
    val name: String, // Nombre del videojuego
    @SerializedName("background_image")
    val image: String, // URL de la imagen de fondo del videojuego
    @SerializedName("metacritic")
    val mcscore: Int, // Puntuación en Metacritic
    @SerializedName("released")
    val datereleased: String, // Fecha de lanzamiento
    @SerializedName("playtime")
    val gameplaytime: Int, // Tiempo de juego estimado en horas
)