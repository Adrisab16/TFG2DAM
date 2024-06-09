package com.example.tfg2dam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tfg2dam.model.UserModel
import com.example.tfg2dam.model.ValoracionMap
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

/**
 * ViewModel para manejar los videojuegos del usuario.
 */
class UserVideogameViewModel: ViewModel() {

    private val firestore = Firebase.firestore

    /**
     * Añade videojuegos (su id) a las 5 listas disponibles del usuario
     *
     * @param userId ID del usuario.
     * @param gameId ID del videojuego a añadir.
     * @param gameType Tipo de lista de videojuegos a la que añadir el juego.
     */

    suspend fun addGameIdToUser(userId: String, gameId: Int, valoracion: Int, gameType: String) {
        try {
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                if (userModel != null) {
                    val updatedGameMap = when (gameType) {
                        "CP" -> {
                            val updatedList = userModel.gameMap.CP.toMutableList()
                            val gameIndex = updatedList.indexOfFirst { it.gameID == gameId }
                            if (gameIndex != -1) {
                                updatedList[gameIndex] = ValoracionMap(gameId, valoracion)
                            } else {
                                updatedList.add(ValoracionMap(gameId, valoracion))
                            }
                            userModel.gameMap.copy(CP = updatedList)
                        }
                        "PTP" -> {
                            val updatedList = userModel.gameMap.PTP.toMutableList()
                            val gameIndex = updatedList.indexOfFirst { it.gameID == gameId }
                            if (gameIndex != -1) {
                                updatedList[gameIndex] = ValoracionMap(gameId, valoracion)
                            } else {
                                updatedList.add(ValoracionMap(gameId, valoracion))
                            }
                            userModel.gameMap.copy(PTP = updatedList)
                        }
                        "DR" -> {
                            val updatedList = userModel.gameMap.DR.toMutableList()
                            val gameIndex = updatedList.indexOfFirst { it.gameID == gameId }
                            if (gameIndex != -1) {
                                updatedList[gameIndex] = ValoracionMap(gameId, valoracion)
                            } else {
                                updatedList.add(ValoracionMap(gameId, valoracion))
                            }
                            userModel.gameMap.copy(DR = updatedList)
                        }
                        "OH" -> {
                            val updatedList = userModel.gameMap.OH.toMutableList()
                            val gameIndex = updatedList.indexOfFirst { it.gameID == gameId }
                            if (gameIndex != -1) {
                                updatedList[gameIndex] = ValoracionMap(gameId, valoracion)
                            } else {
                                updatedList.add(ValoracionMap(gameId, valoracion))
                            }
                            userModel.gameMap.copy(OH = updatedList)
                        }
                        "CTD" -> {
                            val updatedList = userModel.gameMap.CTD.toMutableList()
                            val gameIndex = updatedList.indexOfFirst { it.gameID == gameId }
                            if (gameIndex != -1) {
                                updatedList[gameIndex] = ValoracionMap(gameId, valoracion)
                            } else {
                                updatedList.add(ValoracionMap(gameId, valoracion))
                            }
                            userModel.gameMap.copy(CTD = updatedList)
                        }
                        else -> userModel.gameMap
                    }

                    val updatedUser = userModel.copy(gameMap = updatedGameMap)
                    firestore.collection("users").document(userId).set(updatedUser)
                        .addOnSuccessListener {
                            Log.d("ACTUALIZACIÓN EXITOSA", "Se agregó o actualizó el gameId al usuario correctamente en Firestore")
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
     *
     * @param userId ID del usuario.
     * @param gameId ID del videojuego a eliminar.
     * @param gameType Tipo de lista de videojuegos de la que eliminar el juego.
     * @return Booleano que indica si la eliminación fue exitosa.
     */

    suspend fun removeGameIdFromUser(userId: String, gameId: Int, gameType: String): Boolean {
        return try {
            // Obtener el usuario de Firestore
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                if (userModel != null) {
                    // Intentar buscar el valoracion map de ese id

                    // Actualizar el mapa de juegos eliminando el gameId de la lista correspondiente
                    val updatedGameMap = when (gameType) {
                        "CP" -> userModel.gameMap.copy(CP = userModel.gameMap.CP.toMutableList().apply { remove(userModel.gameMap.CP.filter { valoracion -> valoracion.gameID == gameId }.first()) })
                        "PTP" -> userModel.gameMap.copy(PTP = userModel.gameMap.PTP.toMutableList().apply { remove(userModel.gameMap.PTP.filter { valoracion -> valoracion.gameID == gameId }.first()) })
                        "DR" -> userModel.gameMap.copy(DR = userModel.gameMap.DR.toMutableList().apply { remove(userModel.gameMap.DR.filter { valoracion -> valoracion.gameID == gameId }.first()) })
                        "OH" -> userModel.gameMap.copy(OH = userModel.gameMap.OH.toMutableList().apply { remove(userModel.gameMap.OH.filter { valoracion -> valoracion.gameID == gameId }.first()) })
                        "CTD" -> userModel.gameMap.copy(CTD = userModel.gameMap.CTD.toMutableList().apply { remove(userModel.gameMap.CTD.filter { valoracion -> valoracion.gameID == gameId }.first()) })
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

                    true // Indicar que la eliminación fue exitosa
                } else {
                    false // Indicar que el modelo de usuario es nulo
                }
            } else {
                false // Indicar que el documento de usuario no existe
            }
        } catch (e: Exception) {
            Log.e("ERROR AL ELIMINAR GAME ID", "ERROR: ${e.localizedMessage}", e)
            false // Indicar que ocurrió un error al eliminar el gameId
        }
    }

    /**
     * Obtiene los videojuegos de una lista específica para el usuario actual.
     *
     * IMPORTANTE: Solo obtiene los gameid, ni los muestra ni nada, esta info se le pasa a la vista y se llama a la API para que se muestre
     *
     * @param userId ID del usuario.
     * @param gameType Tipo de lista de videojuegos a obtener.
     * @return Lista de IDs de videojuegos de la lista que me pase por aprámetro o null si ocurre un error.
     */
    suspend fun getVideoGamesByType(userId: String, gameType: String): List<ValoracionMap>? {
        return try {
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                if (userModel?.gameMap != null) {
                    when (gameType) {
                        "CP" -> userModel.gameMap.CP.sortedByDescending { it.valoracion }
                        "PTP" -> userModel.gameMap.PTP.sortedByDescending { it.valoracion }
                        "DR" -> userModel.gameMap.DR.sortedByDescending { it.valoracion }
                        "OH" -> userModel.gameMap.OH.sortedByDescending { it.valoracion }
                        "CTD" -> userModel.gameMap.CTD.sortedBy { it.valoracion }
                        else -> null
                    }
                } else {
                    Log.d("USUARIO INCORRECTO", "El modelo de usuario o el mapa de juegos es nulo")
                    null
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

    suspend fun getValoracion(userId: String, gameId: Int, gameType: String): Int? {
        return try {
            val userDocument = firestore.collection("users").document(userId).get().await()
            if (userDocument.exists()) {
                val userModel = userDocument.toObject(UserModel::class.java)
                userModel?.gameMap?.let {
                    when (gameType) {
                        "CP" -> it.CP.find { valoracion -> valoracion.gameID == gameId }?.valoracion
                        "PTP" -> it.PTP.find { valoracion -> valoracion.gameID == gameId }?.valoracion
                        "DR" -> it.DR.find { valoracion -> valoracion.gameID == gameId }?.valoracion
                        "OH" -> it.OH.find { valoracion -> valoracion.gameID == gameId }?.valoracion
                        "CTD" -> it.CTD.find { valoracion -> valoracion.gameID == gameId }?.valoracion
                        else -> null
                    }
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ERROR AL OBTENER VALORACION", "ERROR: ${e.localizedMessage}", e)
            null
        }
    }
}