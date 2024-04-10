package com.example.tfg2dam.model

data class RawgResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GameModel>
)