package com.example.tfg2dam.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tfg2dam.model.GameMap
import com.example.tfg2dam.model.UserModel
import com.example.tfg2dam.model.toMutableMap
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * ViewModel que gestiona el inicio de sesión y registro de usuarios.
 */
class LoginViewModel: ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set

    /**
     * Intenta iniciar sesión con el email y la contraseña proporcionados.
     * Si el inicio de sesión es exitoso, ejecuta la acción de éxito proporcionada.
     * En caso de error, actualiza el estado para mostrar una alerta.
     *
     * @param onSuccess Acción a ejecutar si el inicio de sesión es exitoso.
     * @param onError Acción a ejecutar si hay un error en el inicio de sesión.
     */
    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "usuario o contraseña incorrectas")
                            onError("Error al iniciar sesión. O el usuario o la contraseña son incorrectas.")
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR DE LA APLICACION", "ERROR: ${e.localizedMessage}")
            }
        }
    }


    /**
     * Crea un nuevo usuario con el email y la contraseña proporcionados.
     * Si el registro es exitoso, guarda la información del usuario y ejecuta la acción de éxito proporcionada.
     * En caso de error, actualiza el estado para mostrar una alerta.
     *
     * @param onSuccess Acción a ejecutar si el registro es exitoso.
     */
    fun createUser(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUser(userName, password)
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Error al crear usuario")
                            Toast.makeText(context, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Actualiza el nombre de usuario.
     *
     * @param userName Nuevo nombre de usuario a establecer.
     */
    fun changeUserName(userName: String) {
        this.userName = userName
    }

    /**
     * Guarda la información del usuario recién registrado en Firestore.
     *
     * @param username Nombre de usuario a guardar.
     */
    private fun saveUser(username: String, password: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        // Verificación de la existencia de un ID y email válidos
        if (id != null && email != null) {
            // Creación del objeto UserModel
            val user = UserModel(
                userId = id,
                email = email,
                username = username,
                password = password,
                gameMap = GameMap(
                    CP = mutableListOf(),
                    PTP = mutableListOf(),
                    DR = mutableListOf(),
                    OH = mutableListOf(),
                    CTD = mutableListOf()
                ).toMutableMap()
            )

            viewModelScope.launch(Dispatchers.IO) {
                // Guardar el usuario en la colección "Users" en Firestore
                firestore.collection("users").document(id).set(user)
                    .addOnSuccessListener {
                        Log.d("USUARIO GUARDADO", "Se guardó el usuario correctamente en Firestore")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("ERROR AL GUARDAR", "ERROR al guardar en Firestore: $exception")
                    }
            }
        } else {
            Log.d("ERROR GUARDAR USUARIO", "El ID de usuario o el correo electrónico son nulos.")
        }
    }

    /**
     * Actualiza el email del usuario.
     *
     * @param email Nuevo email a establecer.
     */
    fun changeEmail(email: String) {
        this.email = email
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        try {
            auth.signOut()
        }catch(_: Exception){
            Log.d("ERROR AL CERRAR SESION", "No se ha podido cerrar sesión")
        }
    }

    /**
     * Elimina la cuenta del usuario actual, tanto de firestore como de authentication.
     *
     * @param onSuccess Acción a ejecutar si la eliminación de la cuenta es exitosa.
     */
    fun deleteAccount(context: Context, onSuccess: () -> Unit) {
        val user = auth.currentUser
        val userId = user?.uid

        // Obtener el ID del documento asociado al ID de usuario
        firestore.collection("users")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val documentId = document.id

                    // Eliminar los datos del usuario en Firestore usando el ID del documento
                    firestore.collection("users")
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            // Eliminar la autenticación del usuario después de eliminar los datos en Firestore
                            user?.delete()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onSuccess()
                                } else {
                                    Toast.makeText(context, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ERROR AL ELIMINAR", "Error al eliminar datos del usuario en Firestore: $exception")
                            Toast.makeText(context, "Error al eliminar datos del usuario en Firestore", Toast.LENGTH_SHORT).show()

                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ERROR AL OBTENER ID", "Error al obtener ID del documento en Firestore: $exception")
                Toast.makeText(context, "Error al obtener ID del documento en Firestore", Toast.LENGTH_SHORT).show()

            }
    }

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param password Nueva contraseña a establecer.
     */
    fun changePassword(password: String) {
        this.password = password
    }

    /**
     * Obtiene el nombre de usuario del usuario actualmente autenticado en Firestore.
     *
     * @param callback Función de retorno que recibe el nombre de usuario como parámetro.
     */
    fun getUsernameFromFirestore(callback: (String?) -> Unit) {
        // Obtener el ID del usuario actualmente autenticado
        val userId = auth.currentUser?.uid

        // Verificar si el usuario está autenticado y tiene un ID válido
        if (userId != null) {
            // Realizar una consulta a Firestore para obtener los datos del usuario
            firestore.collection("users")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // Obtener el nombre de usuario del documento
                        val username = document.getString("username")
                        // Llamar al callback con el nombre de usuario obtenido
                        callback(username)
                        return@addOnSuccessListener
                    }
                    // Si no se encontró ningún documento, llamar al callback con null
                    callback(null)
                }
                .addOnFailureListener { exception ->
                    // Manejar el error si la consulta a Firestore falla
                    Log.e("ERROR DE OBTENCION DE DATOS", "Error al obtener datos del usuario en Firestore: $exception")
                    callback(null)
                }
        } else {
            // Si el ID del usuario es null, llamar al callback con null
            Log.e("ID FALLIDO", "El ID del usuario actual es null")
            callback(null)
        }
    }

    /**
     * Obtiene el ID de usuario del usuario actualmente autenticado en Firestore.
     *
     * @param callback Función de retorno que recibe el ID de usuario como parámetro.
     */
    fun getUserIdFromFirestore(callback: (String) -> Unit) {
        // Obtener el ID del usuario actualmente autenticado
        val userId = auth.currentUser?.uid
        try {
            // Verificar si el usuario está autenticado y tiene un ID válido
            if (userId != null) {
                // Llamar al callback con el ID de usuario obtenido
                callback(userId)
            } else {
                // Si el usuario no está autenticado o no tiene un ID válido, lanzar una excepción
                Log.e("ERROR", "El usuario no está autenticado o no tiene un ID válido")
                throw IllegalStateException("El usuario no está autenticado o no tiene un ID válido")
            }
        } catch (e: Exception) {
            // Manejar cualquier excepción que pueda ocurrir dentro del bloque try
            Log.e("ERROR", "Error al obtener el ID del usuario: ${e.localizedMessage}", e)
        }
    }

    /**
     * Cambia la contraseña del usuario actualmente autenticado en Firebase.
     *
     * @param oldPassword La contraseña antigua del usuario.
     * @param newPassword La nueva contraseña que se establecerá.
     * @param onError Función de retorno que se llama si ocurre un error durante el proceso de cambio de contraseña.
     * Recibe un mensaje de error como parámetro.
     * @param navController NavController que se utilizará para navegar a la pantalla de inicio de sesión después
     * de cambiar la contraseña con éxito.
     */
    fun changePassword(
        oldPassword: String,
        newPassword: String,
        onError: (String) -> Unit,
        navController: NavController,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

                    // Reauthentica al usuario
                    user.reauthenticate(credential).await()

                    // Actualiza la contraseña en Firebase Auth
                    user.updatePassword(newPassword).await()

                    // Actualiza la contraseña en Firestore
                    val userId = user.uid
                    val userDocRef = firestore.collection("users").document(userId)
                    userDocRef.update("password", newPassword).await()

                    // Actualiza la interfaz de usuario en el hilo principal
                    withContext(Dispatchers.Main) {
                        navController.navigate("Login")
                    }

                    // Llama al método logout en el hilo principal
                    withContext(Dispatchers.Main) {
                        logout()
                    }

                    Log.d("Cambio contraseña", "Cambio de contraseña exitoso")

                } else {
                    // Llama a onError en el hilo principal
                    withContext(Dispatchers.Main) {
                        onError("User is not authenticated")
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Error changing password: ${e.localizedMessage}")
                // Llama a onError en el hilo principal
                withContext(Dispatchers.Main) {
                    onError(e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}