package com.example.tfg2dam.model

import com.google.firebase.firestore.PropertyName

data class UserModel(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    @get:PropertyName("gameMap") val gameMap: GameMap = GameMap()
) {
    constructor() : this("", "", "", "", GameMap())
}

data class GameMap(
    @get:PropertyName("CP") val CP: MutableList<Int> = mutableListOf(),
    @get:PropertyName("PTP") val PTP: MutableList<Int> = mutableListOf(),
    @get:PropertyName("DR") val DR: MutableList<Int> = mutableListOf(),
    @get:PropertyName("OH") val OH: MutableList<Int> = mutableListOf(),
    @get:PropertyName("CTD") val CTD: MutableList<Int> = mutableListOf()
) {
    constructor() : this(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
}


fun GameMap.toMutableMap(): GameMap {
    return GameMap(
        CP = CP.toMutableList(),
        PTP = PTP.toMutableList(),
        DR = DR.toMutableList(),
        OH = OH.toMutableList(),
        CTD = CTD.toMutableList()
    )
}

