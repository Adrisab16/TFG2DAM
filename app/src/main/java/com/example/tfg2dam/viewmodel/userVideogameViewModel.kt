package com.example.tfg2dam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tfg2dam.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class userVideogameViewModel: ViewModel() {

    private val firestore = Firebase.firestore
    /**
     * Añade videojuegos (su id) a las 5 listas disponibles
     */

    suspend fun addGameIdToUser(userId: String, gameId: Int, gameType: String) {
        try {
            // Obtener el usuario de Firestore
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                if (userModel != null) {
                    // Actualizar el mapa de juegos agregando el nuevo gameId a la lista existente
                    val updatedGameMap = when (gameType) {
                        "CP" -> userModel.gameMap.copy(CP = userModel.gameMap.CP.toMutableList().apply { add(gameId) })
                        "PTP" -> userModel.gameMap.copy(PTP = userModel.gameMap.PTP.toMutableList().apply { add(gameId) })
                        "DR" -> userModel.gameMap.copy(DR = userModel.gameMap.DR.toMutableList().apply { add(gameId) })
                        "OH" -> userModel.gameMap.copy(OH = userModel.gameMap.OH.toMutableList().apply { add(gameId) })
                        "CTD" -> userModel.gameMap.copy(CTD = userModel.gameMap.CTD.toMutableList().apply { add(gameId) })
                        else -> userModel.gameMap // Si el tipo de juego no es válido, no se realiza ninguna modificación
                    }

                    // Actualizar el objeto UserModel con el nuevo mapa de juegos
                    val updatedUser = userModel.copy(gameMap = updatedGameMap)

                    // Guardar el usuario actualizado en Firestore
                    firestore.collection("users").document(userId).set(updatedUser)
                        .addOnSuccessListener {
                            Log.d("ACTUALIZACIÓN EXITOSA", "Se agregó el gameId al usuario correctamente en Firestore")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ERROR AL ACTUALIZAR", "ERROR al actualizar en Firestore", exception)
                        }
                }
            } else {
                Log.d("USUARIO NO ENCONTRADO", "No se encontró ningún usuario con el ID proporcionado")
            }
        } catch (e: Exception) {
            Log.e("ERROR AL AGREGAR GAME ID", "ERROR: ${e.localizedMessage}", e)
        }
    }

    /**
     * Elimina videojuegos (su id) de las 5 listas disponibles
     */

    suspend fun removeGameIdFromUser(userId: String, gameId: Int, gameType: String) {
        try {
            // Obtener el usuario de Firestore
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                if (userModel != null) {
                    // Actualizar el mapa de juegos eliminando el gameId de la lista correspondiente
                    val updatedGameMap = when (gameType) {
                        "CP" -> userModel.gameMap.copy(CP = userModel.gameMap.CP.toMutableList().apply { remove(gameId) })
                        "PTP" -> userModel.gameMap.copy(PTP = userModel.gameMap.PTP.toMutableList().apply { remove(gameId) })
                        "DR" -> userModel.gameMap.copy(DR = userModel.gameMap.DR.toMutableList().apply { remove(gameId) })
                        "OH" -> userModel.gameMap.copy(OH = userModel.gameMap.OH.toMutableList().apply { remove(gameId) })
                        "CTD" -> userModel.gameMap.copy(CTD = userModel.gameMap.CTD.toMutableList().apply { remove(gameId) })
                        else -> userModel.gameMap // Si el tipo de juego no es válido, no se realiza ninguna modificación
                    }

                    // Actualizar el objeto UserModel con el nuevo mapa de juegos
                    val updatedUser = userModel.copy(gameMap = updatedGameMap)

                    // Guardar el usuario actualizado en Firestore
                    firestore.collection("users").document(userId).set(updatedUser)
                        .addOnSuccessListener {
                            Log.d("ACTUALIZACIÓN EXITOSA", "Se eliminó el gameId del usuario correctamente en Firestore")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ERROR AL ACTUALIZAR", "ERROR al actualizar en Firestore", exception)
                        }
                }
            } else {
                Log.d("USUARIO NO ENCONTRADO", "No se encontró ningún usuario con el ID proporcionado")
            }
        } catch (e: Exception) {
            Log.e("ERROR AL ELIMINAR GAME ID", "ERROR: ${e.localizedMessage}", e)
        }
    }

    // Función para obtener los videojuegos de un tipo específico para el usuario actual.
    // RECUERDA: Solo obtiene los gameid, ni los muestra ni nada, ahora esto hay que pasarselo a la vista y llamar a la API para que los busque
    suspend fun getVideoGamesByType(userId: String, gameType: String): List<Int>? {
        return try {
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                userModel?.gameMap?.let { map ->
                    when (gameType) {
                        "CP" -> map.CP
                        "PTP" -> map.PTP
                        "DR" -> map.DR
                        "OH" -> map.OH
                        "CTD" -> map.CTD
                        else -> null
                    }
                }
            } else {
                Log.d("USUARIO NO ENCONTRADO", "No se encontró ningún usuario con el ID proporcionado")
                null
            }
        } catch (e: Exception) {
            Log.e("ERROR AL OBTENER VIDEOJUEGOS", "ERROR: ${e.localizedMessage}", e)
            null
        }
    }
}