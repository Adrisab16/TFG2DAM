package com.example.tfg2dam.model

import com.google.firebase.firestore.PropertyName

/**
 * Data class que representa un usuario en la aplicación.
 *
 * @property userId Identificador único del usuario.
 * @property username Nombre de usuario.
 * @property email Correo electrónico del usuario.
 * @property password Contraseña del usuario.
 * @property gameMap Mapa de juegos asociado al usuario.
 */
data class UserModel(
    val userId: String = "", // Identificador único del usuario
    val username: String = "", // Nombre de usuario
    val email: String = "", // Correo electrónico del usuario
    val password: String = "", // Contraseña del usuario
    @get:PropertyName("gameMap") val gameMap: GameMap = GameMap() // Mapa de juegos asociado al usuario
) {
    /**
     * Constructor secundario que permite la creación de un `UserModel` con valores predeterminados.
     */
    constructor() : this("", "", "", "", GameMap())
}

/**
 * Data class que representa el mapa de juegos de un usuario.
 *
 * @property CP Lista de identificadores de juegos completados.
 * @property PTP Lista de identificadores de juegos por completar.
 * @property DR Lista de identificadores de juegos abandonados.
 * @property OH Lista de identificadores de juegos en curso.
 * @property CTD Lista de identificadores de juegos completados múltiples veces.
 */
data class GameMap(
    @get:PropertyName("CP") val CP: MutableList<ValoracionMap> = mutableListOf(), // Lista de Juegos completados
    @get:PropertyName("PTP") val PTP: MutableList<ValoracionMap> = mutableListOf(), // Lista de Juegos por completar
    @get:PropertyName("DR") val DR: MutableList<ValoracionMap> = mutableListOf(), // Lista de Juegos abandonados
    @get:PropertyName("OH") val OH: MutableList<ValoracionMap> = mutableListOf(), // Lista de Juegos en espera
    @get:PropertyName("CTD") val CTD: MutableList<ValoracionMap> = mutableListOf(), // Lista de juegos completados
) {
    /**
     * Constructor secundario que permite la creación de un `GameMap` con listas vacías predeterminadas.
     */
    constructor() : this(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
}

/**
 * Data class que representa el mapa de juegos como clave y su valoracion como valor.
 *
 * @property gameID Id del videojuego en cuestión.
 * @property valoracion Numero del 1 al 10 que corresponde a la valoración que el usuario haya introducido para el juego en cuestión.
 */
data class ValoracionMap(
    val gameID: Int,
    val valoracion: Int,
){
    /**
     * Constructor secundario que permite la creación de un `GameMap` con listas vacías predeterminadas.
     */
    constructor() : this(0, 0)
}

/**
 * Extensión de la clase `GameMap` que convierte un `GameMap` en un nuevo `GameMap` con listas mutables.
 *
 * @return Un nuevo objeto `GameMap` con copias mutables de las listas originales.
 */
fun GameMap.toMutableMap(): GameMap {
    return GameMap(
        CP = CP.toMutableList(), // Copia mutable de la lista de juegos completados
        PTP = PTP.toMutableList(), // Copia mutable de la lista de juegos por completar
        DR = DR.toMutableList(), // Copia mutable de la lista de juegos abandonados
        OH = OH.toMutableList(), // Copia mutable de la lista de juegos en curso
        CTD = CTD.toMutableList(), // Copia mutable de la lista de juegos completados
    )
}

