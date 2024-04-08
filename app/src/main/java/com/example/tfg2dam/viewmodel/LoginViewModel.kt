package com.example.tfg2dam.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
/***
class LoginViewModel: ViewModel() {
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
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE","Usuario y/o contrasena incorrectos")
                            showAlert = true
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
                            // DCS - Si se realiza con éxito, almacenamos el usuario en la colección "Users"
                            saveUser(userName)
                            onSuccess()
                        } else {
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
    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email
        val pwd = auth.currentUser?.email


        viewModelScope.launch(Dispatchers.IO) {
            /*val user = UserModel(
                userId = id.toString(),
                email = email.toString(),
                username = username
            )*/
            // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
            firestore.collection("Users")
            //.add(user)
            //.addOnSuccessListener { Log.d("GUARDAR OK", "Se guardó el usuario correctamente en Firestore") }
            //.addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }

    /* DCS - Otra forma de hacer lo mismo...

    Accediendo a "FirebaseFirestore" directamente mediante su método estático getInstance().
    Esto es perfectamente válido y se utiliza en muchas guías y ejemplos de Firebase,
    pero yo prefiero la otra opción, es decir, utilizar la sintaxis de Kotlin para acceder a
    servicios de Firebase, aprovechando la biblioteca firebase-ktx (Kotlin Extensions).
    "Firebase.firestore" es esencialmente un wrapper que llama internamente a FirebaseFirestore.getInstance(),
    proporcionando un acceso más conciso y alineado con las convenciones de Kotlin.

    Además, puede considerarse más idiomática para los desarrolladores de Kotlin, ya que hace
    uso de las extensiones de Kotlin (KTX) de Firebase, lo cual está en línea con las prácticas
    recomendadas para el desarrollo moderno en Kotlin.

    En cuanto a términos de funcionalidad, no hay diferencia; ambas opciones realizarán la operación
    de escritura en Firestore de la misma manera. Pero si ya estamos utilizando las extensiones de Kotlin
    para Firebase en otras partes de nuestra aplicación, usar "Firebase.firestore" puede ayudar a mantener
    la consistencia en nuestra base de código.

    import com.google.firebase.firestore.FirebaseFirestore

    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(
                userId = id.toString(),
                email = email.toString(),
                username = username
            )
            // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
            FirebaseFirestore.getInstance().collection("Users")
                .add(user)
                .addOnSuccessListener { Log.d("GUARDAR OK", "Se guardó el usuario correctamente en Firestore") }
                .addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }
    */

    /**
     * Cierra el diálogo de alerta de error mostrada en la UI.
     */
    fun closeAlert(){
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
}***/