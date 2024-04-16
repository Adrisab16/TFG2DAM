package com.example.tfg2dam.model

import com.google.gson.annotations.SerializedName

data class GameModel(
    @SerializedName("counts")
    val count: Int,
    @SerializedName("results")
    val results: List<VideogamesList>
    // val next: String?,
    // val previous: String?,

)

data class VideogamesList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("background_image")
    val image:String,
)

// Video donde estoy sacando esta info: https://www.youtube.com/watch?v=-oJGFesFBXg