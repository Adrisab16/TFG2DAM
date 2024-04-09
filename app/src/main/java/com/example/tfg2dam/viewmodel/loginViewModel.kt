package com.example.tfg2dam.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg2dam.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class loginViewModel: ViewModel() {
    // DCS - Definición de variables y funciones para manejar el inicio de sesión y registro de usuarios.

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    var showAlert by mutableStateOf(false)
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set
    var selectedTab by mutableIntStateOf(0)
        private set

    /**
     * Método privado para escribir en el archivo de registro.
     */
    private fun writeToLog(action: String) {
        val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val logMessage = "[$timeStamp] $action"
        try {
            FileWriter("F:\\MyProjects\\TFG2DAM\\app\\src\\main\\java\\com\\example\\tfg2dam\\logs", true).use { writer ->
                writer.appendLine(logMessage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Intenta iniciar sesión con el email y la contraseña proporcionados.
     * Si el inicio de sesión es exitoso, ejecuta la acción de éxito proporcionada.
     * En caso de error, actualiza el estado para mostrar una alerta.
     *
     * @param onSuccess Acción a ejecutar si el inicio de sesión es exitoso.
     */
    fun login(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para validar al usuario
                // por email y contraseña
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            writeToLog("Inicio de sesión exitoso")
                            onSuccess()
                        } else {
                            writeToLog("Error al iniciar sesion")
                            Log.d("ERROR EN FIREBASE","Usuario y/o contrasena incorrectos")
                            //showAlert = true
                        }
                    }
            } catch (e: Exception){
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
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
    fun createUser(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            writeToLog("Usuario creado con éxito")
                            // DCS - Si se realiza con éxito, almacenamos el usuario en la colección "Users"
                            saveUser(userName)
                            onSuccess()
                        } else {
                            writeToLog("Error al crear usuario")
                            Log.d("ERROR EN FIREBASE","Error al crear usuario")
                            showAlert = true
                        }
                    }
            } catch (e: Exception){
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Guarda la información del usuario recién registrado en Firestore.
     *
     * @param username Nombre de usuario a guardar.
     */
    @SuppressLint("SuspiciousIndentation")
    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email
        val pwd = auth.currentUser?.email


        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(
                userId = id.toString(),
                email = email.toString(),
                username = username,
                password = pwd.toString(),
                )
            writeToLog("Usuario guardado con éxito")
                // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
                 firestore.collection("Users").add(user)
            //.addOnSuccessListener { Log.d("GUARDAR OK", "Se guardó el usuario correctamente en Firestore") }
            //.addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }

    /**
     * Cierra el diálogo de alerta de error mostrada en la UI.
     */
    fun closeAlert(){
        writeToLog("Alerta cerrada")
        showAlert = false
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
     * Actualiza la contraseña del usuario.
     *
     * @param password Nueva contraseña a establecer.
     */
    fun changePassword(password: String) {
        this.password = password
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
     * Cambia la pestaña seleccionada en la UI.
     *
     * @param selectedTab Índice de la nueva pestaña seleccionada.
     */
    fun changeSelectedTab(selectedTab: Int) {
        this.selectedTab = selectedTab
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        try {
            auth.signOut()
            writeToLog("Cierre de sesion del usuario exitoso")
        }catch(_: Exception){
            writeToLog("Fallo al cerrar sesión")
        }
    }
}